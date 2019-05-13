package com.bzgwl.mybatis_plus.sysOrder.controller;

import com.bzgwl.mybatis_plus.sysOrder.service.IOrdersService;
import com.bzgwl.mybatis_plus.sysOrder.entity.Orders;
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
 * @Date   2019-05-10
 * @Email  logwto@163.com
 */
@RestController
@RequestMapping("/sysOrder/orders")
public class OrdersController {
    @Autowired
    private IOrdersService  ordersServiceImpl;


    @RequestMapping("/findAll")
    public JsonResponse findAll(){
        JsonResponse jsonResponse = new JsonResponse();
        List<Orders> list = ordersServiceImpl.list();

        if(list != null){
            jsonResponse.setData(list);
        }
        return jsonResponse;
    }

    /**
    * 根据条件 分页查询
    * @param orders
    * @param page
    * @param limit
    * @return
    */
    @RequestMapping("/findByParam")
    public JsonResponse findByParam(Orders orders,Integer page , Integer limit){

        JsonResponse jsonResponse = ordersServiceImpl.findByParam(orders, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param orders
    * @return
    */
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
    @RequestMapping("delByIds")
    public JsonResponse delByIds(@RequestParam("ids[]") List<Long> ids){
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
