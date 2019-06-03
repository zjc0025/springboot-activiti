package com.bzgwl.mybatis_plus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bzgwl.mybatis_plus.sys.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 代码生成器
 * Mapper 接口
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {


    /*
    根据用户ID查询所有权限
     */
    @Select("SELECT * FROM permission WHERE id IN ( SELECT DISTINCT(p_id) FROM" +
            " role_permission WHERE r_id IN " +
            "( SELECT r_id FROM user_role WHERE u_id = #{uId} ) )")
    List<Permission> findUserPermission(Integer uId);

    /*
    根据角色ID查询所有权限
     */
    @Select("SELECT * FROM permission WHERE id IN ( SELECT DISTINCT(p_id) FROM role_permission WHERE r_id = #{rId})")
    List<Permission> findPermissionByRid(Integer rId);

    @Select("SELECT * from permission WHERE id in (select DISTINCT(p_id) " +
            "from role_permission WHERE r_id in ( select r_id from user_role " +
            "INNER JOIN role on user_role.r_id = role.id where u_id = #{uId} ) ) " +
            "and parent_id = 1 and is_menu= 1 order by order_num ")
    List<Permission> findFirstMenu(Integer uId);

    @Select("SELECT * from permission WHERE id in (select DISTINCT(p_id) " +
            "from role_permission WHERE r_id in ( select r_id from user_role " +
            "INNER JOIN role on user_role.r_id = role.id where u_id = #{uId} ) ) " +
            "and is_menu= 2 and parent_id = #{pId} order by order_num ")
    List<Permission> findSecondMenu(Integer uId, Integer pId);

    @Select("select max(id) from permission")
    Integer findMaxId();
}
