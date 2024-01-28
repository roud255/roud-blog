package top.roud.roudblogcms.common.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.roudblogcms.common.annotation.CommentAuth;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.RedisUtil;
import top.roud.roudblogcms.common.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static top.roud.roudblogcms.common.result.ResultCode.COMMENTS_DAILY_OVERFLOW;
import static top.roud.roudblogcms.common.result.ResultCode.USER_NO_ACCESS_COMMENT;
import static top.roud.roudblogcms.common.utils.ConstUtil.USER_DAILYCOMMENTSCOUNT;

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
        Map<String, Object> info = tokenUtil.getUserInfoByRequest(request);
        //暂定根据type判断操作权限
        int type = (int)info.get("type");
         if(2==type){
            return Result.failure(USER_NO_ACCESS_COMMENT);
        }
        Long userId = (Long) info.get("id");
        String dailyCountKey = USER_DAILYCOMMENTSCOUNT + userId;
        boolean dailyCountFlag = redisUtil.hasKey(dailyCountKey);
        if(!dailyCountFlag){
            redisUtil.increment(dailyCountKey,String.valueOf(1),1, TimeUnit.DAYS);
        }else {
            String dailyCountStr = redisUtil.get(dailyCountKey);
            long dailyCount = Long.parseLong(dailyCountStr);
            if (dailyCount < maxCount) {
                redisUtil.increment(dailyCountKey,String.valueOf(1),1, TimeUnit.DAYS);
            }else  {
                return Result.failure(COMMENTS_DAILY_OVERFLOW);
            }
        }
        return pjp.proceed();
    }
}
