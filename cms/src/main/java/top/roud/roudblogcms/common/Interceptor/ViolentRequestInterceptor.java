package top.roud.roudblogcms.common.Interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.AutoIdUtil;
import top.roud.roudblogcms.common.utils.ConstUtil;
import top.roud.roudblogcms.common.utils.IPUtil;
import top.roud.roudblogcms.common.utils.RedisUtil;
import top.roud.roudblogcms.common.utils.VisitControlUtil;
import top.roud.roudblogcms.entity.ForbidIP;
import top.roud.roudblogcms.mapper.ForBidIPMapper;
import top.roud.roudblogcms.service.ForBidIPService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static top.roud.roudblogcms.common.result.ResultCode.HAVIOR_INVOKE_ERROR;

@Component
public class ViolentRequestInterceptor implements HandlerInterceptor {
    @Value("${violentRequest.maxCount}")
    private int maxCount;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IPUtil ipUtil;
    @Autowired
    private VisitControlUtil visitControlUtil;
    @Autowired
    private ForBidIPService forBidIPService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = ipUtil.getIpAddr(request);
        String key = ConstUtil.SYS_PREVENT_VIOLENT_REQUESTS + ipAddr;
        if(!redisUtil.hasKey(key)){
            redisUtil.increment(key, String.valueOf(1),1, TimeUnit.DAYS);
        }else {
            long count = Long.parseLong(redisUtil.get(key));
            if(count>maxCount){
                if(StringUtils.isNotBlank(ipAddr) && !forBidIPService.exists(ipAddr)){
                    ForbidIP forbidIP = new ForbidIP();
                    forbidIP.setId(AutoIdUtil.getId());
                    forbidIP.setIp(ipAddr);
                    forbidIP.setSeconds(60*60*24L);
                    forbidIP.setReason("防暴刷拦截器拦截");
                    forbidIP.setTime(new Date());
                    forBidIPService.save(forbidIP);
                    visitControlUtil.ban(ipAddr, 60*60*24L);
                }
                JSONObject json = (JSONObject) JSONObject.toJSON(Result.failure(HAVIOR_INVOKE_ERROR));
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(json);
                return false;
            }
            redisUtil.increment(key, String.valueOf(1),1, TimeUnit.DAYS);
        }
        return true;
    }
}
