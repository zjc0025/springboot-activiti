package com.bzgwl.mybatis_plus;

import com.bzgwl.mybatis_plus.sys.entity.Permission;
import com.bzgwl.mybatis_plus.sys.mapper.PermissionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    @Test
    public void test1(){
        String tableName = "orders";
        excuting("web",tableName,url,username,password,driverClassName);

        Permission permission = new Permission();

        permission.setParentId(75);
        permission.setAuthName("订单");
        permission.setIsMenu(2);
        permission.setOrderNum(6100);
        permission.setMenuUrl("/web/"+tableName+"/mainIndex");
        permissionMapper.insert(permission);

        Integer maxId = permissionMapper.findMaxId();

        Permission add = new Permission();
        add.setIsMenu(0);
        add.setAuthName("新增");
        add.setAuth(tableName+":add");
        add.setOrderNum(6101);
        add.setParentId(maxId);
        permissionMapper.insert(add);

        Permission update = new Permission();
        update.setIsMenu(0);
        update.setAuthName("修改");
        update.setAuth(tableName+":update");
        update.setOrderNum(6102);
        update.setParentId(maxId);
        permissionMapper.insert(update);

        Permission delete = new Permission();
        delete.setIsMenu(0);
        delete.setAuthName("删除");
        delete.setAuth(tableName+":delete");
        delete.setOrderNum(6103);
        delete.setParentId(maxId);
        permissionMapper.insert(delete);

    }
}
