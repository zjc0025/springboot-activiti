package com.bzgwl.mybatis_plus.sys.controller;

import com.bzgwl.mybatis_plus.sys.service.IRolePermissionService;
import com.bzgwl.mybatis_plus.sys.entity.RolePermission;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
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
@RequestMapping("sys/rolepermission")
public class RolePermissionController {
    @Autowired
    private IRolePermissionService  rolePermissionServiceImpl;


    /**
    * list跳转
    * @return
    */
    @RequestMapping("/mainIndex")
    public String mainIndex(){
        return "rolePermission/rolePermission_list";
    }

    /**
    * addOrUpdate 页面跳转
    * @param mv
    * @param rolePermission
    * @return
    */
    @RequestMapping("/addOrUpdateIndex")
    public ModelAndView addOrUpdateIndex(ModelAndView mv ,RolePermission rolePermission){
        mv.setViewName("rolePermission/rolePermission_addOrUpdate");
        if(rolePermission != null){
            mv.addObject("obj",rolePermission);
        }
        return mv;
    }

    /**
    * 根据条件 分页查询
    * @param rolePermission
    * @param page
    * @param limit
    * @return
    */
    @ResponseBody
    @RequestMapping("/findByParams")
    public JsonResponse findByParams(RolePermission rolePermission,Integer page , Integer limit){

        JsonResponse jsonResponse = rolePermissionServiceImpl.findByParam(rolePermission, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param rolePermission
    * @return
    */
    @ResponseBody
    @RequestMapping("/addOrUpdate")
    public JsonResponse addOrUpdate(RolePermission rolePermission){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            rolePermissionServiceImpl.saveOrUpdate(rolePermission);
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
            rolePermissionServiceImpl.removeByIds(ids);
        }catch (Exception e){
            jsonResponse.setCode("1");
            e.printStackTrace();
        }
        return jsonResponse;
    }

 }
