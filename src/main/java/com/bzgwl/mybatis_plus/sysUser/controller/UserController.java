package com.bzgwl.mybatis_plus.sysUser.controller;

import com.bzgwl.mybatis_plus.sysUser.service.IUserService;
import com.bzgwl.mybatis_plus.sysUser.entity.User;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  代码生成器
 *  前端控制器
 * @Author Professor_Kong
 * @Date   2019-05-09
 * @Email  logwto@163.com
 */
@RestController
@RequestMapping("/sysUser/user")
public class UserController {
    @Autowired
    private IUserService  userServiceImpl;


    @RequestMapping("/findAll")
    public JsonResponse findAll(){
        JsonResponse jsonResponse = new JsonResponse();
        List<User> list = userServiceImpl.list();

        if(list != null){
            jsonResponse.setData(list);
        }
        return jsonResponse;
    }

    /**
    * 根据条件 分页查询
    * @param user
    * @param page
    * @param limit
    * @return
    */
    @RequestMapping("/findByParam")
    public JsonResponse findByParam(User user,Integer page , Integer limit){

        JsonResponse jsonResponse = userServiceImpl.findByParam(user, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param user
    * @return
    */
    @RequestMapping("/addOrUpdate")
    public JsonResponse addOrUpdate(User user){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            userServiceImpl.saveOrUpdate(user);
        }catch (Exception e){
            jsonResponse.setCode("1");
            e.printStackTrace();
        }
        return jsonResponse;
    }

    /**
    * 删除
    * @param ids
    * @return
    */
    @RequestMapping("delByIds")
    public JsonResponse delByIds(@RequestParam("ids[]") List<Long> ids){
        JsonResponse jsonResponse = new JsonResponse();

        try {
            userServiceImpl.removeByIds(ids);
        }catch (Exception e){
            jsonResponse.setCode("1");
            e.printStackTrace();
        }

        return jsonResponse;
    }

 }
