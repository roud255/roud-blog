package top.roud.cms.common.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.annotation.OperationAuth;
import top.roud.cms.common.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static top.roud.cms.common.result.ResultCode.TOKEN_INVALID;
import static top.roud.cms.common.result.ResultCode.USER_NO_ACCESS;

/**
 * @description : TODO
 * @author: rod
 * @date: 2023/4/25
 * @version:
 */
@Aspect
@Component
public class OperationAuthAspect {
    @Pointcut("@annotation(operationAuth)")
    public void pointCut(OperationAuth operationAuth){

    }
    @Around("pointCut(operationAuth)")
    public Object around(ProceedingJoinPoint pjp,OperationAuth operationAuth) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();
        Assert.notNull(request, "request can not null");
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token) || !JwtUtil.checkSign(token)){
            return Result.failure(TOKEN_INVALID);
        }
        Map<String, Object> info = JwtUtil.getInfo(token);
        //暂定根据type判断操作权限
        int type = (int)info.get("type");
        if(0==type){
            return pjp.proceed();
        }
        return Result.failure(USER_NO_ACCESS);
    }

}
