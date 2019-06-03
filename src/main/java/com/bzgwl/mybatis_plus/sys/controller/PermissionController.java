package com.bzgwl.mybatis_plus.sys.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bzgwl.mybatis_plus.sys.entity.Permission;
import com.bzgwl.mybatis_plus.sys.entity.User;
import com.bzgwl.mybatis_plus.sys.service.IPermissionService;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  代码生成器
 *  前端控制器
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
@Controller
@SuppressWarnings("all")  //抑制各种黄线警告
@RequestMapping("sys/permission")
public class PermissionController {
    @Autowired
    private IPermissionService  permissionServiceImpl;


    /**
    * list跳转
    * @return
    */
    @RequestMapping("/mainIndex")
    public String mainIndex(){
        return "permission/permission";
    }

    /**
    * addOrUpdate 页面跳转
    * @param mv
    * @param permission
    * @return
    */
    @RequestMapping("/addOrUpdateIndex")
    public ModelAndView addOrUpdateIndex(ModelAndView mv ,Permission permission){
        mv.setViewName("permission/permission_addOrUpdate");
        if(permission != null){
            mv.addObject("obj",permission);
        }
        return mv;
    }


    /**
    * 查询
    * @return
    */
    @ResponseBody
    @RequestMapping("/findAll")
    public JsonResponse findByParams(){

        JsonResponse jsonResponse = new JsonResponse();
        Wrapper wrapper = new QueryWrapper();
        ((QueryWrapper) wrapper).orderByAsc("order_num");
        List<Permission> list = permissionServiceImpl.list(wrapper);
        jsonResponse.setData(list);
        jsonResponse.setCount(Long.valueOf(list.size()));
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param permission
    * @return
    */
    @ResponseBody
    @RequestMapping("/addOrUpdate")
    public JsonResponse addOrUpdate(Permission permission){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            if(permission.getId()==null){
                Integer isMenu = permission.getIsMenu();
                permission.setCreateUser(3);
                permission.setStatus("1");
                permission.setCreateTime(new Date());
                permission.setUpdateTime(new Date());
                permission.setChecked(0);
                permission.setParentId(permission.getParentId());
            }
            permission.setUpdateTime(new Date());
            permissionServiceImpl.saveOrUpdate(permission);
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
    public JsonResponse delByIds(Integer id){
        JsonResponse jsonResponse = new JsonResponse();
        if(id == null){
            jsonResponse.setCode("1");
            jsonResponse.setMsg("ID 不能为空!");
            return jsonResponse;
        }
        try {
            permissionServiceImpl.deleteById(id);
        }catch (Exception e){
            jsonResponse.setCode("1");
            jsonResponse.setMsg("Error 请联系管理员!");
            e.printStackTrace();
        }
        return jsonResponse;
    }

    /**
     * 查询当前用户菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMenuByUser")
    public List getMenuByUser(){
        //获取当前登陆用户
        Subject currentUser = SecurityUtils.getSubject();
        User user = currentUser.getPrincipals().oneByType(User.class);

        return permissionServiceImpl.getMenuByUser(user);
    }

    @ResponseBody
    @RequestMapping("/getPermissionByRoleId")
    public List getPermissionByRoleId(Integer id){
        List arrayList = new ArrayList();
        if(id!=null){
             arrayList = permissionServiceImpl.getPermissionByRoleId(id);
        }
        return arrayList;
    }

    @Autowired
    private IPermissionService permissionService;

    @ResponseBody
    @RequestMapping("grant")
    public JsonResponse grant(Integer rid ,@RequestParam("ids[]") List<Integer> ids){
        JsonResponse js = new JsonResponse();
        js.setMsg("操作成功！");
        try{
            permissionService.upRolePmLink(rid,ids);
        }catch (Exception e){
            js.setCode("1");
            js.setMsg("操作失败！请联系管理员!");
            e.printStackTrace();
        }
        return js;
    }

    @ResponseBody
    @RequestMapping("getPByLevel")
    public List<Permission> getPByLevel(Integer level){
        if(level==null){
            return new ArrayList<>();
        }
        List<Permission> pByLevel = permissionService.getPByLevel(level);
        return pByLevel;
    }

 }
