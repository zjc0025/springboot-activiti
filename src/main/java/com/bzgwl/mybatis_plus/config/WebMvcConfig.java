package com.bzgwl.mybatis_plus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/process/**").addResourceLocations("classpath:/process/");
        registry.addResourceHandler("/static/**/**/**/**").addResourceLocations("classpath:/static/**/**/**/");
//        registry.addResourceHandler("/static/css/**").addResourceLocations("classpath:/static/css/");
//        registry.addResourceHandler("/static/lib/**/**").addResourceLocations("classpath:/static/lib/**/");
//        registry.addResourceHandler("/static/lib/layui/css/**").addResourceLocations("classpath:/static/lib/layui/css/");
//        registry.addResourceHandler("/static/lib/layui/lay/modules/**").addResourceLocations("classpath:/static/lib/layui/lay/modules/");
    }
}
