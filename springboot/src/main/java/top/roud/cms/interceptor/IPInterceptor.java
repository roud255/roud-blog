package top.roud.cms.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description : TODO
 * @author: roud
 * @date: 2022/9/28
 * @version:
 */
public class IPInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String ip = request.getRemoteAddr();
        //
        return true;

    }
}
