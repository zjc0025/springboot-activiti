package com.bzgwl.mybatis_plus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author Professor_Kong
 * @Description
 * @Date 2019/11/15
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .anyRequest().permitAll().and().logout().permitAll()//配置不需要登录验证
                    .and().headers().frameOptions().sameOrigin() // 解决  frame because it set 'X-Frame-Options' to 'deny'
                    .and().csrf().disable() //关闭跨站请求伪造
            ;
        }

//    X-Frame-Options是一个HTTP标头（header），用来告诉浏览器这个网页是否可以放在iFrame内；
//    X-Frame-Options: DENY     //表示该页面不允许在 frame 中展示，即便是在相同域名的页面中嵌套也不允许。。
//    X-Frame-Options: SAMEORIGIN    //表示该页面可以在相同域名页面的 frame 中展示。
//    X-Frame-Options: ALLOW-FROM http://caibaojian.com/   //表示该网页只能放在http://caibaojian.com//网页架设的iFrame内
}
