package com.bzgwl.mybatis_plus.sys.service.impl;

import com.bzgwl.mybatis_plus.sys.entity.UserRole;
import com.bzgwl.mybatis_plus.sys.mapper.RoleMapper;
import com.bzgwl.mybatis_plus.sys.mapper.UserRoleMapper;
import com.bzgwl.mybatis_plus.sys.service.IUserRoleService;
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
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
@Service
@SuppressWarnings("all")  //抑制各种黄线警告
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired(required = false)
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> list() {

       return userRoleMapper.selectList(null);
    }

    /**
    * 分页查询
    *
    * @param userRole  page  limit
    * @return jsonResponse
    */
    @Override
    public JsonResponse findByParam(UserRole userRole,Integer page , Integer limit) {

        JsonResponse jsonResponse = new JsonResponse();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10 ;
        }
        //开启分页
        Page userRolePage = new Page(page,limit);
        //查询构造器
        Wrapper wrapper = new QueryWrapper();

        if(userRole.getId()!=null && !"".equals(userRole.getId())){
            ((QueryWrapper) wrapper).eq("id",userRole.getId());
        }
        if(userRole.getUId()!=null && !"".equals(userRole.getUId())){
            ((QueryWrapper) wrapper).eq("u_id",userRole.getUId());
        }
        if(userRole.getRId()!=null && !"".equals(userRole.getRId())){
            ((QueryWrapper) wrapper).eq("r_id",userRole.getRId());
        }
        if(userRole.getCreatetime()!=null && !"".equals(userRole.getCreatetime())){
            ((QueryWrapper) wrapper).eq("createtime",userRole.getCreatetime());
        }
        if(userRole.getUpdatetime()!=null && !"".equals(userRole.getUpdatetime())){
            ((QueryWrapper) wrapper).eq("updatetime",userRole.getUpdatetime());
        }
        if(userRole.getStatus()!=null && !"".equals(userRole.getStatus())){
            ((QueryWrapper) wrapper).eq("status",userRole.getStatus());
        }
        IPage<UserRole> userRoleIPage = userRoleMapper.selectPage(userRolePage, wrapper);

        jsonResponse.setData(userRoleIPage.getRecords());
        jsonResponse.setCount(userRolePage.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }

    /**
     * 维护 用户 角色 关系
     *
     * @param uid
     * @param roleIds
     */
    @Override
    public void upUsRoLink(Integer uid, List<Integer> roleIds) {
        Wrapper wrapper = new QueryWrapper();
        ((QueryWrapper) wrapper).eq("u_id",uid);
        userRoleMapper.delete(wrapper);

        for(Integer id : roleIds){
            UserRole userRole = new UserRole();
            userRole.setUId(uid);
            userRole.setRId(id);
            userRoleMapper.insert(userRole);
        }
    }
}
