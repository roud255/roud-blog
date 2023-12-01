package top.roud.cms.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.roud.cms.common.interceptor.JwtInterceptor;
import top.roud.cms.common.interceptor.ViolentRequestInterceptor;

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
    public ViolentRequestInterceptor violentRequestInterceptor;
    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/manage/**").addPathPatterns("/user/**").addPathPatterns("/aat/del/**")
                .addPathPatterns("/img/upload");
        registry.addInterceptor(violentRequestInterceptor).addPathPatterns("/**");
    }
}
