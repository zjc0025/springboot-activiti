package com.bzgwl.mybatis_plus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bzgwl.mybatis_plus.sys.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * 代码生成器
 * Mapper 接口
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select name from role where id in (select r_id from user_role where u_id = #{id} and status = 1 )")
    Set<String> findRoleNameByUser(Integer id);

    /**
     * 根据用户id 查询用户角色
     * @param id
     * @return
     */
    @Select("select * from role where id in (select r_id from user_role where u_id =#{id} )")
    List<Role> getRoleById(Integer id);

}
