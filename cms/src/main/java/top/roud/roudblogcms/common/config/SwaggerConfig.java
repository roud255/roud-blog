package top.roud.roudblogcms.common.config;

/**
 * @description:
 * @author: guangrui_hu
 * @date: 2024/1/26
 * @version: 1.0.0
 */
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket buildDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("若得博客(roudblog)接口文档")
                        .description("若得博客(roudblog)相关接口文档")
                        .termsOfServiceUrl("")
                        .contact(new Contact("roudblog", "https://blog.roud.top", "2553112258@qq.com"))
                        .version("1.0")
                        .build()
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.roud.roudblogcms.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
