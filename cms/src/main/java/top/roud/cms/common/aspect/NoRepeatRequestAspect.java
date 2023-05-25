package top.roud.cms.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.result.ResultCode;
import top.roud.cms.common.annotation.NoRepeatRequest;
import top.roud.cms.common.utils.IPUtil;
import top.roud.cms.common.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
    @Pointcut("@annotation(noRepeatRequest)")
    public void pointCut(NoRepeatRequest noRepeatRequest) {
    }

    @Around("pointCut(noRepeatRequest)")
    public Object around(ProceedingJoinPoint pjp, NoRepeatRequest noRepeatRequest) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Assert.notNull(request, "request can not null");
        int seconds = noRepeatRequest.seconds();
        int maxCount = noRepeatRequest.maxCount();
        String ip= IPUtil.getIpAddr(request);
        String key = request.getServletPath() + "-" + ip ;
        Integer count = (Integer) redisUtil.get(key);
        Optional<Integer> op = Optional.ofNullable(count);

        if (!op.isPresent()) {
            redisUtil.set(key,1, seconds);
            return pjp.proceed();
        }
        if (count < maxCount) {
            count = count+1;
            redisUtil.set(key, count, 0, 1001);
            return pjp.proceed();
        }else  {
            return Result.failure(ResultCode.REQUEST_FAST_REFUSE, "请求过于频繁！");
        }
    }
}