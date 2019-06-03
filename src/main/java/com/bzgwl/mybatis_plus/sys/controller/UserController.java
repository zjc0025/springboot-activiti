package com.bzgwl.mybatis_plus.sys.controller;

import com.bzgwl.mybatis_plus.sys.service.IUserRoleService;
import com.bzgwl.mybatis_plus.sys.service.IUserService;
import com.bzgwl.mybatis_plus.sys.entity.User;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  代码生成器
 *  前端控制器
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
@Controller
@SuppressWarnings("all")  //抑制各种黄线警告
@RequestMapping("sys/user")
public class UserController {
    @Autowired
    private IUserService  userServiceImpl;

    @Autowired
    private IUserRoleService userRoleService;

    /**
    * list跳转
    * @return
    */
    @RequestMapping("/mainIndex")
    public String mainIndex(){
        return "user/user_list";
    }

    /**
    * addOrUpdate 页面跳转
    * @param mv
    * @param user
    * @return
    */
    @RequiresPermissions(value={"user:add", "user:update"}, logical= Logical.OR)
    @RequestMapping("/addOrUpdateIndex")
    public ModelAndView addOrUpdateIndex(ModelAndView mv ,User user){
        mv.setViewName("user/user_addOrUpdate");
        if(user != null){
            mv.addObject("obj",user);
        }
        return mv;
    }

    /**
    * 根据条件 分页查询
    * @param user
    * @param page
    * @param limit
    * @return
    */
//    @RequiresRoles(value = {"admin"})
//    @RequiresPermissions("user:list")
    @ResponseBody
    @RequestMapping("/findByParams")
    public JsonResponse findByParams(User user,Integer page , Integer limit){

        JsonResponse jsonResponse = userServiceImpl.findByParam(user, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param user
    * @return
    */
    @RequiresRoles(value = {"admin"})
    @ResponseBody
    @RequestMapping("/addOrUpdate")
    public JsonResponse addOrUpdate(User user ,@RequestParam("ids[]") List<Integer> ids){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            userServiceImpl.saveOrUpdate(user);
            userRoleService.upUsRoLink(user.getId(),ids);
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
    @RequiresPermissions("user:delete")
    @ResponseBody
    @RequestMapping("delByIds")
    public JsonResponse delByIds(@RequestParam("ids[]") List<Integer> ids){
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
