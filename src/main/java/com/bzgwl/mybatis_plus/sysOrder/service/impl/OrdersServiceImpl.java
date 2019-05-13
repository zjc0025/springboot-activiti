package com.bzgwl.mybatis_plus.sysOrder.service.impl;

import com.bzgwl.mybatis_plus.sysOrder.entity.Orders;
import com.bzgwl.mybatis_plus.sysOrder.mapper.OrdersMapper;
import com.bzgwl.mybatis_plus.sysOrder.service.IOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 *  代码生成器
 *  服务实现类
 * @Author Professor_Kong
 * @Date   2019-05-10
 * @Email  logwto@163.com
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Autowired(required = false)
    private OrdersMapper ordersMapper;

    @Override
    public List<Orders> list() {

       return ordersMapper.selectList(null);
    }

    /**
    * 分页查询
    *
    * @param orders  page  limit
    * @return jsonResponse
    */
    @Override
    public JsonResponse findByParam(Orders orders,Integer page , Integer limit) {

        JsonResponse jsonResponse = new JsonResponse();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10 ;
        }
        //开启分页
        Page ordersPage = new Page(page,limit);
        //查询构造器
        Wrapper wrapper = new QueryWrapper();

        if(orders.getOrderid()!=null && !"".equals(orders.getOrderid())){
            ((QueryWrapper) wrapper).eq("orderid",orders.getOrderid());
        }
        if(orders.getCreateor()!=null && !"".equals(orders.getCreateor())){
            ((QueryWrapper) wrapper).eq("createor",orders.getCreateor());
        }
        if(orders.getCreatetime()!=null && !"".equals(orders.getCreatetime())){
            ((QueryWrapper) wrapper).eq("createtime",orders.getCreatetime());
        }
        if(orders.getCounts()!=null && !"".equals(orders.getCounts())){
            ((QueryWrapper) wrapper).eq("counts",orders.getCounts());
        }
        if(orders.getOrdername()!=null && !"".equals(orders.getOrdername())){
            ((QueryWrapper) wrapper).eq("ordername",orders.getOrdername());
        }
        if(orders.getStatus()!=null && !"".equals(orders.getStatus())){
            ((QueryWrapper) wrapper).eq("status",orders.getStatus());
        }
        IPage<Orders> ordersIPage = ordersMapper.selectPage(ordersPage, wrapper);

        jsonResponse.setData(ordersIPage.getRecords());
        jsonResponse.setCount(ordersPage.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }

}
