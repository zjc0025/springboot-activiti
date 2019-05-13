package com.bzgwl.mybatis_plus.sysOrder.service;

import com.bzgwl.mybatis_plus.sysOrder.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bzgwl.mybatis_plus.utils.JsonResponse;

 /**
 *  代码生成器
 *  service 接口
 * @Author Professor_Kong
 * @Date   2019-05-10
 * @Email  logwto@163.com
 */
public interface IOrdersService extends IService<Orders> {

 /**
 * 分页查询
 * @param orders
 * @param page
 * @param limit
 * @return
 */
 JsonResponse findByParam(Orders orders,Integer page , Integer limit);
}
