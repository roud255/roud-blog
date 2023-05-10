package top.roud.cms.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.roud.cms.interceptor.JwtInterceptor;

/**
 * @ClassName: InterceptorConfig
 * @Description:
 * @Author roud
 * @Date 2022/9/24
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/manage/**").addPathPatterns("/user/**").addPathPatterns("/aat/del/**");
    }
}
