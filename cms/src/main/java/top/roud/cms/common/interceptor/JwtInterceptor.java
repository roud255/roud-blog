package top.roud.cms.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.roud.cms.common.result.Result;
import top.roud.cms.common.utils.JwtUtil;
import top.roud.cms.common.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static top.roud.cms.common.result.ResultCode.TOKEN_INVALID;

/**
 * @ClassName: JwtInterceptor
 * @Description: \
 * @Author roud
 * @Date 2022/9/24
 * @Version 1.0
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    public TokenUtil tokenUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JSONObject json;
        String token = tokenUtil.getToken(request);
        if(StringUtils.isBlank(token)){
            json=(JSONObject) JSONObject.toJSON(Result.failure(TOKEN_INVALID));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }
        boolean flag = JwtUtil.checkSign(token);
        if(flag){
            return true;
        }
        json=(JSONObject) JSONObject.toJSON(Result.failure(TOKEN_INVALID));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
