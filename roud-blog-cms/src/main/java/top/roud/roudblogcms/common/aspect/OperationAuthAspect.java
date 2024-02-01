package top.roud.roudblogcms.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.roudblogcms.common.annotation.OperationAuth;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static top.roud.roudblogcms.common.result.ResultCode.SYSTEM_ERROR;
import static top.roud.roudblogcms.common.result.ResultCode.USER_NO_ACCESS;

/**
 * @description : TODO
 * @author: rod
 * @date: 2023/4/25
 * @version:
 */
@Aspect
@Component
public class OperationAuthAspect {
    @Autowired
    private TokenUtil tokenUtil;

    @Pointcut("@annotation(operationAuth)")
    public void pointCut(OperationAuth operationAuth){

    }
    @Around("pointCut(operationAuth)")
    public Object around(ProceedingJoinPoint pjp,OperationAuth operationAuth) throws Throwable {
        ServletRequestAttributes ra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null == ra){
            return Result.failure(SYSTEM_ERROR);
        }
        HttpServletRequest request = ra.getRequest();

        Map<String, Object> info = tokenUtil.getUserInfoByRequest(request);
        //暂定根据type判断操作权限
        int type = (int)info.get("type");
        if(0==type){
            return pjp.proceed();
        }
        return Result.failure(USER_NO_ACCESS);
    }

}
