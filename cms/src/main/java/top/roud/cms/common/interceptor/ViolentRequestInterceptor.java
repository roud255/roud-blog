package top.roud.cms.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.utils.ConstUtil;
import top.roud.cms.common.utils.IPUtil;
import top.roud.cms.common.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static top.roud.cms.common.result.ResultCode.HAVIOR_INVOKE_ERROR;

@Component
public class ViolentRequestInterceptor implements HandlerInterceptor {
    @Value("${violentRequest.maxCount}")
    private int maxCount;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IPUtil ipUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = ipUtil.getIpAddr(request);
        String key = ConstUtil.REDIS_PREVENT_VIOLENT_REQUESTS_KEY + ipAddr;
        Integer count = (Integer) redisUtil.get(key);
        if(!Optional.ofNullable(count).isPresent()){
            redisUtil.set(key,1,60*60*24);
        }else {
            if(count>maxCount){
                JSONObject json = (JSONObject) JSONObject.toJSON(Result.failure(HAVIOR_INVOKE_ERROR));
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(json);
                return false;
            }
            count++;
            redisUtil.set(key,count,0,1001);
        }
        return true;
    }
}
