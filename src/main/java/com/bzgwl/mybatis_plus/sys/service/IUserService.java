package com.bzgwl.mybatis_plus.sys.service;

import com.bzgwl.mybatis_plus.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bzgwl.mybatis_plus.utils.JsonResponse;

 /**
 *  代码生成器
 *  service 接口
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
public interface IUserService extends IService<User> {

 /**
 * 分页查询
 * @param user
 * @param page
 * @param limit
 * @return
 */
 JsonResponse findByParam(User user,Integer page , Integer limit);
}
