package top.roud.cms.common.aspect;

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
import top.roud.cms.common.result.Result;
import top.roud.cms.common.annotation.CommentAuth;
import top.roud.cms.common.result.ResultCode;
import top.roud.cms.common.utils.JwtUtil;
import top.roud.cms.common.utils.RedisUtil;
import top.roud.cms.common.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.*;
import static top.roud.cms.common.utils.ConstUtil.REDIS_USER_DAILYCOMMENTSCOUNT_KEY;

@Aspect
@Component
public class CommentAuthAspect {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TokenUtil tokenUtil;

    @Pointcut("@annotation(commentAuth)")
    public void pointCut(CommentAuth commentAuth){

    }
    @Around("pointCut(commentAuth)")
    public Object around(ProceedingJoinPoint pjp, CommentAuth commentAuth) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        int maxCount = commentAuth.dailyMaxCount();
        String token = tokenUtil.getToken(request);
        if(StringUtils.isBlank(token)){
            return Result.failure(USER_NOT_LOGIN);
        }
        Map<String, Object> info = JwtUtil.getInfo(token);
        //暂定根据type判断操作权限
        int type = (int)info.get("type");
         if(2==type){
            return Result.failure(USER_NO_ACCESS_COMMENT);
        }
        Long userId = (Long) info.get("id");
        String dailyCountKey = REDIS_USER_DAILYCOMMENTSCOUNT_KEY + userId;
        Integer dailyCount = (Integer) redisUtil.get(dailyCountKey);
        if(!Optional.ofNullable(dailyCount).isPresent()){
            redisUtil.set(dailyCountKey,1,60*60*24);
        }else {
            if (dailyCount < maxCount) {
                dailyCount = dailyCount+1;
                redisUtil.set(dailyCountKey, dailyCount, 0, 1001);
            }else  {
                return Result.failure(COMMENTS_DAILY_OVERFLOW);
            }
        }
        return pjp.proceed();
    }
}
