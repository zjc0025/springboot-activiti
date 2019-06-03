package com.bzgwl.mybatis_plus.sys.controller;

import com.bzgwl.mybatis_plus.sys.service.IUserRoleService;
import com.bzgwl.mybatis_plus.sys.entity.UserRole;
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
@RequestMapping("sys/userrole")
public class UserRoleController {
    @Autowired
    private IUserRoleService  userRoleServiceImpl;


    /**
    * list跳转
    * @return
    */
    @RequestMapping("/mainIndex")
    public String mainIndex(){
        return "userRole/userRole_list";
    }

    /**
    * addOrUpdate 页面跳转
    * @param mv
    * @param userRole
    * @return
    */
    @RequestMapping("/addOrUpdateIndex")
    public ModelAndView addOrUpdateIndex(ModelAndView mv ,UserRole userRole){
        mv.setViewName("userRole/userRole_addOrUpdate");
        if(userRole != null){
            mv.addObject("obj",userRole);
        }
        return mv;
    }

    /**
    * 根据条件 分页查询
    * @param userRole
    * @param page
    * @param limit
    * @return
    */
    @ResponseBody
    @RequestMapping("/findByParams")
    public JsonResponse findByParams(UserRole userRole,Integer page , Integer limit){

        JsonResponse jsonResponse = userRoleServiceImpl.findByParam(userRole, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param userRole
    * @return
    */
    @ResponseBody
    @RequestMapping("/addOrUpdate")
    public JsonResponse addOrUpdate(UserRole userRole){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            userRoleServiceImpl.saveOrUpdate(userRole);
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
            userRoleServiceImpl.removeByIds(ids);
        }catch (Exception e){
            jsonResponse.setCode("1");
            e.printStackTrace();
        }
        return jsonResponse;
    }

 }
