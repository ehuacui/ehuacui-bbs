package org.ehuacui.bbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                //.genericModelSubstitutes(ResponseBodyInfo.class, ResponseDataBody.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.ehuacui.bbs"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("zhoujw", "https://github.com/zhoujw8792", "617726909@qq.com");
        return new ApiInfoBuilder()
                .title("EHuaCui BBS RestFul APIs")
                .description("EHuaCui BBS RestFul APIs")
                .termsOfServiceUrl("http://bbs.ehuacui.org")
                .contact(contact)
                .version("1.0")
                .build();
    }

}
