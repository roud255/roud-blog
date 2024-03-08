package top.roud.roudblogcms.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.roud.roudblogcms.common.Interceptor.AuthInterceptor;
import top.roud.roudblogcms.common.Interceptor.ViolentRequestInterceptor;

/**
 * @ClassName: InterceptorConfig
 * @Description:
 * @Author roud
 * @Date 2022/9/24
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private ViolentRequestInterceptor violentRequestInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(violentRequestInterceptor).addPathPatterns("/**").order(1);
        registry.addInterceptor(authInterceptor).addPathPatterns("/manage/**").addPathPatterns("/user/**").addPathPatterns("/aat/del/**").addPathPatterns("/login/quit").order(2);
    }
}
