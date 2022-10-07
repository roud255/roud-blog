package top.roud.cms.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import top.roud.cms.common.Result;
import top.roud.cms.entity.ForbidIP;
import top.roud.cms.service.ForBidIPService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static top.roud.cms.common.ResultCode.PERMISSION_NO_ACCESS;

/**
 * @description : TODO
 * @author: roud
 * @date: 2022/9/28
 * @version:
 */
public class IPInterceptor implements HandlerInterceptor {
    @Resource
    private ForBidIPService forBidIPService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr();
        try{
            forBidIPService.selectForBidIPByIp(ip);
            JSONObject json=(JSONObject) JSONObject.toJSON(Result.failure(PERMISSION_NO_ACCESS));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }catch (NullPointerException e){
            return true;
        }
    }
}
