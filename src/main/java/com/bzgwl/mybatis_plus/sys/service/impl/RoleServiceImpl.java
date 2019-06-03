package com.bzgwl.mybatis_plus.sys.service.impl;

import com.bzgwl.mybatis_plus.sys.entity.Role;
import com.bzgwl.mybatis_plus.sys.mapper.RoleMapper;
import com.bzgwl.mybatis_plus.sys.mapper.UserRoleMapper;
import com.bzgwl.mybatis_plus.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 *  代码生成器
 *  服务实现类
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
@Service
@SuppressWarnings("all")  //抑制各种黄线警告
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired(required = false)
    private RoleMapper roleMapper;


    @Override
    public List<Role> list() {

       return roleMapper.selectList(null);
    }

    /**
    * 分页查询
    *
    * @param role  page  limit
    * @return jsonResponse
    */
    @Override
    public JsonResponse findByParam(Role role,Integer page , Integer limit) {

        JsonResponse jsonResponse = new JsonResponse();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10 ;
        }
        //开启分页
        Page rolePage = new Page(page,limit);
        //查询构造器
        Wrapper wrapper = new QueryWrapper();

        if(role.getId()!=null && !"".equals(role.getId())){
            ((QueryWrapper) wrapper).eq("id",role.getId());
        }
        if(role.getName()!=null && !"".equals(role.getName())){
            ((QueryWrapper) wrapper).eq("name",role.getName());
        }
        if(role.getDes()!=null && !"".equals(role.getDes())){
            ((QueryWrapper) wrapper).eq("des",role.getDes());
        }
        if(role.getCreateuser()!=null && !"".equals(role.getCreateuser())){
            ((QueryWrapper) wrapper).eq("createuser",role.getCreateuser());
        }
        if(role.getCreatetime()!=null && !"".equals(role.getCreatetime())){
            ((QueryWrapper) wrapper).eq("createtime",role.getCreatetime());
        }
        if(role.getUpdatetime()!=null && !"".equals(role.getUpdatetime())){
            ((QueryWrapper) wrapper).eq("updatetime",role.getUpdatetime());
        }
        if(role.getStatus()!=null && !"".equals(role.getStatus())){
            ((QueryWrapper) wrapper).eq("status",role.getStatus());
        }
        IPage<Role> roleIPage = roleMapper.selectPage(rolePage, wrapper);

        jsonResponse.setData(roleIPage.getRecords());
        jsonResponse.setCount(rolePage.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }


    /**
     * 查询所以角色  ，当前用户角色选中
     *
     * @param id
     * @return
     */
    @Override
    public List<Role> getRoleById(Integer id) {

        //全部角色
        List<Role> roles = roleMapper.selectList(null);

        if(id!= null) {
            //用户拥有角色
            List<Role> roleById = roleMapper.getRoleById(id);

            roles.forEach(p -> {

                for (Role role : roleById) {
                    if (p.getId() == role.getId()) {
                        p.setStatus("1");
                        break;
                    }
                }
            });
        }
        return roles;
    }
}
