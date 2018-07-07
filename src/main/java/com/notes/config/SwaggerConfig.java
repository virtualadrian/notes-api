
package com.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

   @Bean
   public Docket api() {

       return new Docket(DocumentationType.SWAGGER_2).select()
           .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
           .paths(PathSelectors.any())
           .build().apiInfo(apiInfo())
           .forCodeGeneration(true);
   }

   @Bean
   public ApiInfo apiInfo() {
       return new ApiInfoBuilder().contact(contact()).title("Loyalty Tracking App")
                                  .description("Check out <a href='https://github.com/virtualadrian/loyalty-api'>GitHub</a> for more information.")
                                  .build();
   }

   @Bean
   public Contact contact() {
       return new Contact("Loyalty Tracking App", "https://github.com/virtualadrian/loyalty-api",
                          "adrian@adrian.work");
   }

   @Bean
   public WebMvcConfigurerAdapter redirectBaseContextToSwaggerUI() {
       return new WebMvcConfigurerAdapter() {
           @Override
           public void addResourceHandlers(final ResourceHandlerRegistry registry) {
               registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

               registry.addResourceHandler("/webjars/**")
                       .addResourceLocations("classpath:/META-INF/resources/webjars/");
           }

           @Override
           public void addViewControllers(final ViewControllerRegistry registry) {
               registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
           }
       };
   }
}
