package top.roud.roudblogcms.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.roudblogcms.common.annotation.AccessIPRecord;
import top.roud.roudblogcms.common.utils.IPUtil;
import top.roud.roudblogcms.common.utils.LoggerUtil;
import top.roud.roudblogcms.common.utils.TaskExecutePoolUtil;

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
    private IPUtil ipUtil;
    @Autowired
    private TaskExecutePoolUtil taskExecutePoolUtil;
    @Pointcut("@annotation(accessIPRecord)")
    public void pointCut(AccessIPRecord accessIPRecord) {
    }

    @Around("pointCut(accessIPRecord)")
    public Object around(ProceedingJoinPoint pjp, AccessIPRecord accessIPRecord) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Object proceed = pjp.proceed();
        taskExecutePoolUtil.myTaskAsyncPool().execute(()->{
            Assert.notNull(request, "request can not null");
            String ip= ipUtil.getIpAddr(request);;
            String method = request.getMethod();
            String path = request.getServletPath();
            LoggerUtil.ip_record.info("{}|{}|{}", ip, path, method);
        });
        return proceed;
    }

}
