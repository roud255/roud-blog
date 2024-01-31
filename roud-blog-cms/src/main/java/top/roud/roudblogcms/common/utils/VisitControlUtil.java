package top.roud.roudblogcms.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.result.ResultCode;
import top.roud.roudblogcms.entity.ForbidIP;
import top.roud.roudblogcms.mapper.ForBidIPMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: roud
 * @date: 2024/1/29
 * @version: 1.0.0
 */
@Component
public class VisitControlUtil {
    @Autowired
    private TaskExecutePoolUtil taskExecutePoolUtil;

    @Autowired
    private ForBidIPMapper forBidIPMapper;

    @Value("${roudblog.visitcontrol.blacklist.path}")
    private String PATH;

    @Value("${roudblog.visitcontrol.nginx.reloadscript.path}")
    private String nginxReloadScript;

    @Value("${roudblog.visitcontrol.blacklist.logpath}")
    private String LOGPATH;

    private Lock lock = new ReentrantLock();

    private Result writeIP(String path, String ip) {
        lock.lock();
        try{
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("deny "+ip+";\n");
            bufferedWriter.flush();
            fileWriter.close();
            bufferedWriter.close();
            reloadNginx();
        }catch (Exception e) {
            LoggerUtil.ex.error("VisitControlUtil|writeIP|{}", e.getMessage());
        }finally {
            lock.unlock();
        }
        return Result.success();
    }

    private Result removeIP(String path, String ip) {
        lock.lock();
        try {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line1;
            StringBuilder sb1 = new StringBuilder();
            while ((line1 = reader.readLine()) != null) {
                sb1.append(line1).append("\n");
            }
            String s1 = sb1.toString();
            String s2 = s1.replace("deny " + ip + ";\n", "");
            reader.close();
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(s2);
            bufferedWriter.flush();
            bufferedWriter.close();
            reloadNginx();
            LambdaQueryWrapper<ForbidIP> forbidIPLambdaQueryWrapper = new LambdaQueryWrapper<>();
            forbidIPLambdaQueryWrapper.eq(ForbidIP::getIp, ip);
            forBidIPMapper.delete(forbidIPLambdaQueryWrapper);
        } catch (Exception e) {
            LoggerUtil.ex.error("VisitControlUtil|removeIP|{}", e.getMessage());
        }finally {
            lock.unlock();
        }
        return Result.success();
    }

    private Result ban(String path, String ip, long seconds) {
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            try{
                writeIP(path, ip);
                LoggerUtil.blockingIP.info("IP封禁|{}|{}秒", ip, seconds);
                Timer timer=new Timer();
                TimerTask timerTask = new TimerTask() {
                    long t = seconds;
                    public void run() {
                        t--;
                        if (t <= 0) {
                            removeIP(path, ip);
                            LoggerUtil.blockingIP.info("IP解封|{}", ip);
                            cancel();
                            timer.cancel();
                        }
                    }
                };
                timer.schedule(timerTask,0,1000);
            }catch (Exception ex){
                LoggerUtil.ex.error("VisitControlUtil|banip|{}", ex.getMessage());
            }
        });
        return Result.success();
    }

    public Result ban(String ip, long seconds) {
        return ban(PATH, ip, seconds);
    }

    public Result removeIP(String ip) {
        return removeIP(PATH, ip);
    }
    public Result writeIP(String ip) {
        return writeIP(PATH, ip);
    }

    public Result reloadNginx() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(nginxReloadScript);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
            }
        } catch (IOException | InterruptedException e) {
            // Handle exception
            LoggerUtil.ex.error("VisitControlUtil|reloadNginx|{}", e.getMessage());
        }
        return Result.success();
    }

    public String getBlockingIPLog2(){
        String msg = "";
        lock.lock();
        try {
            File file = new File(LOGPATH);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line1;
            StringBuilder sb1 = new StringBuilder();
            while ((line1 = reader.readLine()) != null) {
                sb1.append("<p>").append(line1).append("</p>");
            }
            msg = sb1.toString();
        } catch (Exception e) {
            LoggerUtil.ex.error("VisitControlUtil|getBlockingIPLog3|{}", e.getMessage());
        }finally {
            lock.unlock();
        }
        return msg;
    }

    public List<Map> getBlockingIPLog(){
        LinkedList<Map> list = new LinkedList<>();
        lock.lock();
        try {
            File file = new File(LOGPATH);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line1;
            while ((line1 = reader.readLine()) != null) {
                try{
                    HashMap<String, String> map = new HashMap<>();
                    String[] s = line1.split("\\[.+?\\]");
                    String time = s[0].trim();
                    map.put("time", time);
                    String[] s2 = s[1].split("-");
                    String[] s3 = (s2[1].trim()).split("\\|");
                    if(s3.length >= 2){
                        String type = s3[0];
                        map.put("type", type);
                        String ip = s3[1];
                        map.put("ip", ip);
                    }
                    if (s3.length >= 3){
                        String blockingTime = s3[2];
                        map.put("blockingTime", blockingTime);
                    }
                    list.add(map);
                }catch (Exception e){
                    LoggerUtil.ex.error("VisitControlUtil|getBlockingIPLog|{}|{}", line1, e.getMessage());
                }
            }
        } catch (Exception e) {
            LoggerUtil.ex.error("VisitControlUtil|getBlockingIPLog|{}", e.getMessage());
        }finally {
            lock.unlock();
        }
        return list;
    }
}
