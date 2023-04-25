package top.roud.cms.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.cms.common.annotation.AccessIPRecord;
import top.roud.cms.utils.LoggerUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @description : TODO
 * @author: guangrui_hu
 * @date: 2023/4/25
 * @version:
 */
@Aspect
@Component
public class AccessIPRecordAspect {
    @Pointcut("@annotation(accessIPRecord)")
    public void pointCut(AccessIPRecord accessIPRecord) {
    }

    @Around("pointCut(accessIPRecord)")
    public Object around(ProceedingJoinPoint pjp, AccessIPRecord accessIPRecord) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Assert.notNull(request, "request can not null");
        String ip=request.getRemoteAddr();
        String method = request.getMethod();
        String path = request.getServletPath();
        LoggerUtil.ip_record.info("{}|{}|{}",ip,path,method);
        return pjp.proceed();
    }

}
