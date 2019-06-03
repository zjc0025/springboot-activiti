package com.bzgwl.mybatis_plus.sys.service;

import com.bzgwl.mybatis_plus.sys.entity.Menu;
import com.bzgwl.mybatis_plus.sys.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bzgwl.mybatis_plus.sys.entity.User;
import com.bzgwl.mybatis_plus.sys.entity.ZNode;
import com.bzgwl.mybatis_plus.utils.JsonResponse;

import java.util.List;

/**
 *  代码生成器
 *  service 接口
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
public interface IPermissionService extends IService<Permission> {

  /**
  * 分页查询
  * @param permission
  * @param page
  * @param limit
  * @return
  */
  JsonResponse findByParam(Permission permission,Integer page , Integer limit);

 /**
  * 查询用户菜单
  * @param user
  * @return
  */
  List<Menu> getMenuByUser(User user);

    /**
     * 查询所有权限  前角色权限选中
     * @param id
     * @return
     */
  List<ZNode> getPermissionByRoleId(Integer id);

    /**
     * 维护 角色 权限关系表
     * @param rid
     * @param pids
     */
  void upRolePmLink (Integer rid, List<Integer> pids);

    /**
     * 获取全部一级 或 二级菜单
     * @return
     */
  List<Permission> getPByLevel(Integer level);

    /**
     * 删除权限 如果是一级则 删除下级 及按钮 ，同时删除 角色权限相关 数据
     * @param id
     */
  void deleteById(Integer id);
}
