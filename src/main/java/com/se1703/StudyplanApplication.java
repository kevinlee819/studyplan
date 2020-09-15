package com.se1703;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 * @author leekejin
 * @date 2020-9-2 11:18:47
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.se1703")
public class StudyplanApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(StudyplanApplication.class, args);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        // Swagger-ui映射
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

}
