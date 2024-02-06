package top.roud.roudblogcms.common.aspect;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.roud.roudblogcms.common.annotation.AccessIPRecord;
import top.roud.roudblogcms.common.utils.IPUtil;
import top.roud.roudblogcms.common.utils.LoggerUtil;
import top.roud.roudblogcms.common.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @description : TODO
 * @author: roud
 * @date: 2023/4/25
 * @version:
 */
@Order(1)
@Aspect
@Component
public class AccessIPRecordAspect {
    @Autowired
    private IPUtil ipUtil;
    @Autowired
    private TokenUtil tokenUtil;
    @Pointcut("@annotation(accessIPRecord)")
    public void pointCut(AccessIPRecord accessIPRecord) {
    }

    @Around("pointCut(accessIPRecord)")
    public Object around(ProceedingJoinPoint pjp, AccessIPRecord accessIPRecord) throws Throwable {
        ServletRequestAttributes sra= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null == sra){
            LoggerUtil.ex.error("获取ServletRequestAttributes失败");
            return pjp.proceed();
        }
        HttpServletRequest request = sra.getRequest();
        Object proceed = pjp.proceed();
        String ip= ipUtil.getIpAddr(request);
        if(null == ip){
            LoggerUtil.ip_record.info("获取IP为空，请检查|request:{}", JSONObject.toJSONString(request));
        }
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String userName;
        try {
            Map<String, Object> userInfo = tokenUtil.getUserInfoByRequest(request);
            userName = (String) userInfo.get("name");
        }catch (Exception e){
            userName = "未知";
        }
        LoggerUtil.ip_record.info("ip:{}|method:{}|uri:{}|userName:{}", ip, method, uri, userName);
        return proceed;
    }

}
