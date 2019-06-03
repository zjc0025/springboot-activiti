package com.bzgwl.mybatis_plus.web.service.impl;

import com.bzgwl.mybatis_plus.web.entity.Orders;
import com.bzgwl.mybatis_plus.web.mapper.OrdersMapper;
import com.bzgwl.mybatis_plus.web.service.IOrdersService;
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
 * @Date   2019-05-29
 * @Email  logwto@163.com
 */
@Service
@SuppressWarnings("all")  //抑制各种黄线警告
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

        if(orders.getId()!=null && !"".equals(orders.getId())){
            ((QueryWrapper) wrapper).eq("id",orders.getId());
        }
        if(orders.getOrderName()!=null && !"".equals(orders.getOrderName())){
            ((QueryWrapper) wrapper).eq("order_name",orders.getOrderName());
        }
        if(orders.getOrderType()!=null && !"".equals(orders.getOrderType())){
            ((QueryWrapper) wrapper).eq("order_type",orders.getOrderType());
        }
        if(orders.getCount()!=null && !"".equals(orders.getCount())){
            ((QueryWrapper) wrapper).eq("count",orders.getCount());
        }
        if(orders.getOrderNum()!=null && !"".equals(orders.getOrderNum())){
            ((QueryWrapper) wrapper).eq("order_num",orders.getOrderNum());
        }
        if(orders.getCreateTime()!=null && !"".equals(orders.getCreateTime())){
            ((QueryWrapper) wrapper).eq("create_time",orders.getCreateTime());
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
