package com.bzgwl.mybatis_plus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.bzgwl.mybatis_plus.utils.MyGenerator.excuting;


//@RunWith(SpringJUnit4ClassRunner.class)
//@TestPropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
//classes = MybatisPlusApplication.class,
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeneratorTest {


    @Value("${spring.datasource.url}")
    private  String url;

    @Value("${spring.datasource.username}")
    private  String username;

    @Value("${spring.datasource.password}")
    private  String password;

    @Value("${spring.datasource.driverClassName}")
    private  String driverClassName;

    @Test
    public void test1(){
        excuting("sysOrder","orders",url,username,password,driverClassName);
    }
}
