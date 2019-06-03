package com.bzgwl.mybatis_plus.sys.service;

import com.bzgwl.mybatis_plus.sys.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bzgwl.mybatis_plus.utils.JsonResponse;

 /**
 *  代码生成器
 *  service 接口
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
public interface IRolePermissionService extends IService<RolePermission> {

 /**
 * 分页查询
 * @param rolePermission
 * @param page
 * @param limit
 * @return
 */
 JsonResponse findByParam(RolePermission rolePermission,Integer page , Integer limit);
}
