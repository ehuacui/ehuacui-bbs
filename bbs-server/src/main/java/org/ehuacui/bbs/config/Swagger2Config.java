package org.ehuacui.bbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Configuration
@ComponentScan("org.ehuacui.bbs")
public class Swagger2Config {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                //.genericModelSubstitutes(ResponseBodyInfo.class, ResponseDataBody.class)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Report Documentation", "Report Documentation", "1.0", "urn:tos",
                ApiInfo.DEFAULT_CONTACT, "Report LICENSE", "http://www.apache.org/licenses/LICENSE-2.0");
    }

}
