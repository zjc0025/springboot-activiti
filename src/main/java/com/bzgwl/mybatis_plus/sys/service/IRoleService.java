package com.bzgwl.mybatis_plus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bzgwl.mybatis_plus.sys.entity.Role;
import com.bzgwl.mybatis_plus.utils.JsonResponse;

import java.util.List;

/**
 *  代码生成器
 *  service 接口
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
public interface IRoleService extends IService<Role> {

 /**
 * 分页查询
 * @param role
 * @param page
 * @param limit
 * @return
 */
 JsonResponse findByParam(Role role,Integer page , Integer limit);

 /**
  * 查询所以角色  ，当前用户角色选中
  * @param id
  * @return
  */
 List<Role> getRoleById(Integer id);
}
