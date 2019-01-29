package com.sum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }
    }

//    @Bean(name ="templateResolver")	
//    public ServletContextTemplateResolver getTemplateResolver() {
//    	ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
//    	templateResolver.setPrefix("/WEB-INF/views/");
//    	templateResolver.setPrefix("/WEB-INF/templates/");
//    	templateResolver.setSuffix(".html");
//    	templateResolver.setTemplateMode("XHTML");
//	    return templateResolver;
//    }
//    @Bean(name ="templateEngine")	    
//    public SpringTemplateEngine getTemplateEngine() {
//    	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//    	templateEngine.setTemplateResolver(getTemplateResolver());
//	    return templateEngine;
//    }
//    @Bean(name="viewResolver")
//    public ThymeleafViewResolver getViewResolver(){
//    	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver(); 
//    	viewResolver.setTemplateEngine(getTemplateEngine());
//	    return viewResolver;
//    }
}
