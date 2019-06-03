package com.bzgwl.mybatis_plus.sys.controller;

import com.bzgwl.mybatis_plus.sys.service.IRoleService;
import com.bzgwl.mybatis_plus.sys.entity.Role;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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
@RequestMapping("sys/role")
public class RoleController {
    @Autowired
    private IRoleService  roleServiceImpl;


    /**
    * list跳转
    * @return
    */
    @RequestMapping("/mainIndex")
    public String mainIndex(){
        return "role/role_list";
    }


    @RequestMapping("/grantIndex")
    public String grant(){
        return "role/role_grant";
    }
    /**
    * addOrUpdate 页面跳转
    * @param mv
    * @param role
    * @return
    */
    @RequestMapping("/addOrUpdateIndex")
    public ModelAndView addOrUpdateIndex(ModelAndView mv ,Role role){
        mv.setViewName("role/role_addOrUpdate");
        if(role != null){
            mv.addObject("obj",role);
        }
        return mv;
    }

    /**
    * 根据条件 分页查询
    * @param role
    * @param page
    * @param limit
    * @return
    */
    @ResponseBody
    @RequestMapping("/findByParams")
    public JsonResponse findByParams(Role role,Integer page , Integer limit){

        JsonResponse jsonResponse = roleServiceImpl.findByParam(role, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param role
    * @return
    */
    @ResponseBody
    @RequestMapping("/addOrUpdate")
    public JsonResponse addOrUpdate(Role role){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            roleServiceImpl.saveOrUpdate(role);
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
    @ResponseBody
    @RequestMapping("delByIds")
    public JsonResponse delByIds(@RequestParam("ids[]") List<Long> ids){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            roleServiceImpl.removeByIds(ids);
        }catch (Exception e){
            jsonResponse.setCode("1");
            e.printStackTrace();
        }
        return jsonResponse;
    }

    @RequestMapping("/getRoleById")
    @ResponseBody
    public List<Role> getRoleById(Integer id){
        return roleServiceImpl.getRoleById(id);
    }

 }
