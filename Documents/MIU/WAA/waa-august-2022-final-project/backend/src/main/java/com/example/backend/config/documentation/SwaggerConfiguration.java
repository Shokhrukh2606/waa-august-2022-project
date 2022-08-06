package com.example.backend.config.documentation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${enable.swagger.plugin:true}")
    private boolean enableSwaggerPlugin;

    @Bean
    public Docket swaggerConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.backend"))
                .paths(PathSelectors.any())
                .build()
                .enable(enableSwaggerPlugin)
                .apiInfo(apiDetails());

    }

    private ApiInfo apiDetails(){
        return new ApiInfoBuilder()
                .title("Alumni Management Portal APIs")
                .description("WAA Aug-2022 block - Final Project APIs")
                .contact(new Contact("Alumni Management Portal", "https://localhost:8081", ""))
                .license("Group 4")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .build();
    }

}
