package top.roud.cms.common;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.cms.common.annotation.CommentAuth;
import top.roud.cms.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static top.roud.cms.common.ResultCode.USER_NOT_LOGIN;
import static top.roud.cms.common.ResultCode.USER_NO_ACCESS_COMMENT;

@Aspect
@Component
public class CommentAuthAspect {
    @Pointcut("@annotation(commentAuth)")
    public void pointCut(CommentAuth commentAuth){

    }
    @Around("pointCut(commentAuth)")
    public Object around(ProceedingJoinPoint pjp, CommentAuth commentAuth) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Assert.notNull(request, "request can not null");
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)){
            return Result.failure(USER_NOT_LOGIN);
        }
        Map<String, Object> info = JwtUtil.getInfo(token);
        //暂定根据type判断操作权限
        int type = (int)info.get("type");
        if(2!=type){
            return pjp.proceed();
        }
        return Result.failure(USER_NO_ACCESS_COMMENT);
    }
}
