package com.example.demo.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    public static final String TAG_AUTH_CONTROLLER_DESCRIPTION = "Used to authenticate and get JSON Web Token";
    public static final String TAG_USER_CONTROLLER_DESCRIPTION = "Used to manipulate users";

    @Bean
    public Docket api(){
        Docket docket= new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())  // methods in REST controllers
                .build();
        //Customize Swagger with Metadata
        docket.apiInfo(metaData());
        // allows to select protocol to send requests with in documentation
        docket.protocols(new HashSet< >(Arrays.asList("HTTP","HTTPS")));
        return docket;
    }

    private ApiInfo metaData(){
        Contact contact=new Contact("Mindaugas Jasiunas", "https://github.com/MindaugasJasiunas", "email");
        return new ApiInfo("REST API documentation", "REST API documentation created using Swagger 2", "1.0", "termsOfService url", contact, "license (Apache License Version 2.0)", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList< >());
    }

}
