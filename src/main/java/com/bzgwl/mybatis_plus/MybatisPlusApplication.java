package com.bzgwl.mybatis_plus;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.bzgwl.mybatis_plus.exception.GlobalDefaultExceptionHandler;
import com.bzgwl.mybatis_plus.web.activitiListeners.MyExecutionListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@MapperScan("com.bzgwl.mybatis_plus.sysUser.mapper,com.bzgwl.mybatis_plus.sys.mapper")
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

    //H5引入shiro类库
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    //实例化     异常处理类
    @Bean
    public GlobalDefaultExceptionHandler globalDefaultExceptionHandler(){
        return  new GlobalDefaultExceptionHandler();
    }
}
