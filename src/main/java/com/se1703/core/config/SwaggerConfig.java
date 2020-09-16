package com.se1703.core.config;

import com.se1703.core.constant.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;





/**
 * @author leekejin
 * @date 2020-9-2 10:45:51
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger-switch:true}")
    private boolean swaggerSwitch;

    /**
     * 创建文档
     */
    @Bean
    public Docket userestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .enable(swaggerSwitch)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.se1703"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Api文档基本信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学习养成计划")
                .description("学习养成计划")
                .termsOfServiceUrl("http://localhost:8080")
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        ArrayList<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("authorization",Constant.HEADER_TOKEN_NAME, "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "jwtToken")
        };
        ArrayList<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(new SecurityReference("authorization", authorizationScopes));
        // 安全上下文,所有路径都带Token
        ArrayList<SecurityContext> securityContextList = new ArrayList<>();
        securityContextList.add(SecurityContext.builder()
                .securityReferences(securityReferenceList)
                .forPaths(PathSelectors.any())
                .build());
        return securityContextList;
    }
}
