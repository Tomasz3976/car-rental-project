package com.example.carrentalproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

        @Bean
        public Docket swaggerApi() {
                return new Docket(DocumentationType.SWAGGER_2)
                        .select()
                        .paths(PathSelectors.regex("^(?!/(error).*$).*$"))
                        .build();
        }

}
