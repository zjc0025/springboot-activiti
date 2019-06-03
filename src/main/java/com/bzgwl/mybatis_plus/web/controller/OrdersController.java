package com.bzgwl.mybatis_plus.web.controller;

import com.bzgwl.mybatis_plus.web.service.IOrdersService;
import com.bzgwl.mybatis_plus.web.entity.Orders;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * @Date   2019-05-29
 * @Email  logwto@163.com
 */
@Controller
@SuppressWarnings("all")  //抑制各种黄线警告
@RequestMapping("web/orders")
public class OrdersController {
    @Autowired
    private IOrdersService  ordersServiceImpl;


    /**
    * list跳转
    * @return
    */
    @RequestMapping("/mainIndex")
    public String mainIndex(){
        return "orders/orders_list";
    }

    /**
    * addOrUpdate 页面跳转
    * @param mv
    * @param orders
    * @return
    */
    @RequiresPermissions(value={"orders:add", "orders:update"}, logical= Logical.OR)
    @RequestMapping("/addOrUpdateIndex")
    public ModelAndView addOrUpdateIndex(ModelAndView mv ,Orders orders){
        mv.setViewName("orders/orders_addOrUpdate");
        if(orders != null){
            mv.addObject("obj",orders);
        }
        return mv;
    }

    /**
    * 根据条件 分页查询
    * @param orders
    * @param page
    * @param limit
    * @return
    */
    @ResponseBody
    @RequestMapping("/findByParams")
    public JsonResponse findByParams(Orders orders,Integer page , Integer limit){

        JsonResponse jsonResponse = ordersServiceImpl.findByParam(orders, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param orders
    * @return
    */
    @RequiresPermissions(value={"orders:add", "orders:update"}, logical= Logical.OR)
    @ResponseBody
    @RequestMapping("/addOrUpdate")
    public JsonResponse addOrUpdate(Orders orders){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            ordersServiceImpl.saveOrUpdate(orders);
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
    @RequiresPermissions("orders:delete")
    @ResponseBody
    @RequestMapping("delByIds")
    public JsonResponse delByIds(@RequestParam("ids[]") List<Integer> ids){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            ordersServiceImpl.removeByIds(ids);
        }catch (Exception e){
            jsonResponse.setCode("1");
            e.printStackTrace();
        }
        return jsonResponse;
    }

 }
