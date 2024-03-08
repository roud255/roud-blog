package top.roud.roudblogcms.common.Interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.roud.roudblogcms.common.result.Result;
import top.roud.roudblogcms.common.utils.TokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static top.roud.roudblogcms.common.result.ResultCode.TOKEN_INVALID;


/**
 * @ClassName: JwtInterceptor
 * @Description: \
 * @Author roud
 * @Date 2022/9/24
 * @Version 1.0
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    public TokenUtil tokenUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JSONObject json;
        if(tokenUtil.checkTokenByRequest(request)){
            return true;
        }
        json = (JSONObject) JSONObject.toJSON(Result.failure(TOKEN_INVALID));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
