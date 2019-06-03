package com.bzgwl.mybatis_plus.sys.service;

import com.bzgwl.mybatis_plus.sys.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bzgwl.mybatis_plus.utils.JsonResponse;

import java.util.List;

/**
 *  代码生成器
 *  service 接口
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
public interface IUserRoleService extends IService<UserRole> {

 /**
 * 分页查询
 * @param userRole
 * @param page
 * @param limit
 * @return
 */
 JsonResponse findByParam(UserRole userRole,Integer page , Integer limit);

 /**
  * 维护 用户  角色 关系
  * @param uid
  * @param roleIds
  */
 void upUsRoLink(Integer uid , List<Integer> roleIds);
}
