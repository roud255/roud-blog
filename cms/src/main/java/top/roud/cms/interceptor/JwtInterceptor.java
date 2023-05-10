package top.roud.cms.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import top.roud.cms.common.result.Result;
import top.roud.cms.utils.JwtUtil;

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
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        JSONObject json;
        try {
            JwtUtil.checkSign(token);
            return true;
        }
//        catch (SignatureException e){
//            json=(JSONObject) JSONObject.toJSON(Result.failure(SIGN_INVALID));
//        } catch (TokenExpiredException e){
//            json=(JSONObject) JSONObject.toJSON(Result.failure(TOKEN_EXPIRED));
//        } catch (ArithmeticException e){
//            json=(JSONObject) JSONObject.toJSON(Result.failure(TOKEN_ARITHMETERROR));
        catch (Exception e){
            json=(JSONObject) JSONObject.toJSON(Result.failure(TOKEN_INVALID));
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
