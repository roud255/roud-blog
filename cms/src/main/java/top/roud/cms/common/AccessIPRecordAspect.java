package top.roud.cms.common;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.entity.LogContnet;
import top.roud.cms.utils.IPUtil;
import top.roud.cms.utils.LoggerUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/4/25
 * @version:
 */
@Aspect
@Component
public class AccessIPRecordAspect {
    @Autowired
    private LogContnet logContnet;

    @Pointcut("@annotation(accessIPRecord)")
    public void pointCut(AccessIPRecord accessIPRecord) {
    }

    @Around("pointCut(accessIPRecord)")
    public Object around(ProceedingJoinPoint pjp, AccessIPRecord accessIPRecord) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Assert.notNull(request, "request can not null");
        String ip= IPUtil.getIpAddr(request);;
        String method = request.getMethod();
        String path = request.getServletPath();
        Object proceed = pjp.proceed();
        //pjp.getArgs()获取方法请求参数
        //pjp.proceed()获取方法执行结果

        Object[] args = pjp.getArgs();
        Object[] arguments  = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        String paramter = "";
        if (arguments != null) {
            try {
                paramter = JSONObject.toJSONString(arguments);
            } catch (Exception e) {
                paramter = arguments.toString();
            }
        }
        logContnet.setContent(paramter);

        LoggerUtil.ip_record.info("{}|{}|{}|{}|{}",ip,path,method,JSONObject.toJSONString(logContnet), JSONObject.toJSONString(proceed));
        return proceed;
    }

}
