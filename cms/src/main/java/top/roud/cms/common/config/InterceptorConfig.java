package top.roud.cms.common.config;

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
    @Bean
    public ViolentRequestInterceptor violentRequestInterceptor(){
        return new ViolentRequestInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/manage/**").addPathPatterns("/user/**").addPathPatterns("/aat/del/**")
                .addPathPatterns("/img/upload");
        registry.addInterceptor(violentRequestInterceptor()).addPathPatterns("/**");
    }
}
