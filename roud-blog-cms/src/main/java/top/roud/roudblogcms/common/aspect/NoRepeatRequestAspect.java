package top.roud.roudblogcms.common.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.roudblogcms.common.annotation.NoRepeatRequest;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.result.ResultCode;
import top.roud.roudblogcms.common.utils.IPUtil;
import top.roud.roudblogcms.common.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static top.roud.roudblogcms.common.result.ResultCode.SYSTEM_ERROR;

/**
 * @ClassName: NoRepeatRequestAspect
 * @Description:
 * @Author roud
 * @Date 2022/9/5
 * @Version 1.0
 */
@Aspect
@Component
public class NoRepeatRequestAspect {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IPUtil ipUtil;
    @Pointcut("@annotation(noRepeatRequest)")
    public void pointCut(NoRepeatRequest noRepeatRequest) {
    }

    @Around("pointCut(noRepeatRequest)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatRequest noRepeatRequest) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null == ra){
            return Result.failure(SYSTEM_ERROR);
        }
        HttpServletRequest request = ra.getRequest();
        int seconds = noRepeatRequest.seconds();
        int maxCount = noRepeatRequest.maxCount();
        String ip= ipUtil.getIpAddr(request);
        String requestURI = request.getRequestURI();
        if(StringUtils.isBlank(requestURI)){
            return pjp.proceed();
        }
        String key = requestURI.replace("/", ".").replace("\\", ".") + "." + ip ;
        if(!redisUtil.hasKey(key)){
            return pjp.proceed();
        }
        long count = Long.parseLong(redisUtil.get(key));
        if (count < maxCount) {
            redisUtil.increment(key, String.valueOf(1),seconds, TimeUnit.SECONDS);
            return pjp.proceed();
        }else  {
            return Result.failure(ResultCode.REQUEST_FAST_REFUSE, "请求过于频繁！");
        }
    }
}