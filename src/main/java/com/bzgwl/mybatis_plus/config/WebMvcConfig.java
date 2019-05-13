package com.bzgwl.mybatis_plus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig  extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Static/**").addResourceLocations("classpath:/Static/");
        registry.addResourceHandler("/static/layui/**").addResourceLocations("classpath:/static/layui/");
        registry.addResourceHandler("/static/layui/css/**").addResourceLocations("classpath:/static/layui/css/");
        super.addResourceHandlers(registry);
    }
}
