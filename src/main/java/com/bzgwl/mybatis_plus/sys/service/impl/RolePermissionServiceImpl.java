package com.bzgwl.mybatis_plus.sys.service.impl;

import com.bzgwl.mybatis_plus.sys.entity.RolePermission;
import com.bzgwl.mybatis_plus.sys.mapper.RolePermissionMapper;
import com.bzgwl.mybatis_plus.sys.service.IRolePermissionService;
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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

    @Autowired(required = false)
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermission> list() {

       return rolePermissionMapper.selectList(null);
    }

    /**
    * 分页查询
    *
    * @param rolePermission  page  limit
    * @return jsonResponse
    */
    @Override
    public JsonResponse findByParam(RolePermission rolePermission,Integer page , Integer limit) {

        JsonResponse jsonResponse = new JsonResponse();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10 ;
        }
        //开启分页
        Page rolePermissionPage = new Page(page,limit);
        //查询构造器
        Wrapper wrapper = new QueryWrapper();

        if(rolePermission.getId()!=null && !"".equals(rolePermission.getId())){
            ((QueryWrapper) wrapper).eq("id",rolePermission.getId());
        }
        if(rolePermission.getRId()!=null && !"".equals(rolePermission.getRId())){
            ((QueryWrapper) wrapper).eq("r_id",rolePermission.getRId());
        }
        if(rolePermission.getPId()!=null && !"".equals(rolePermission.getPId())){
            ((QueryWrapper) wrapper).eq("p_id",rolePermission.getPId());
        }
        if(rolePermission.getCreatetime()!=null && !"".equals(rolePermission.getCreatetime())){
            ((QueryWrapper) wrapper).eq("createtime",rolePermission.getCreatetime());
        }
        if(rolePermission.getUpdatetime()!=null && !"".equals(rolePermission.getUpdatetime())){
            ((QueryWrapper) wrapper).eq("updatetime",rolePermission.getUpdatetime());
        }
        if(rolePermission.getStatus()!=null && !"".equals(rolePermission.getStatus())){
            ((QueryWrapper) wrapper).eq("status",rolePermission.getStatus());
        }
        IPage<RolePermission> rolePermissionIPage = rolePermissionMapper.selectPage(rolePermissionPage, wrapper);

        jsonResponse.setData(rolePermissionIPage.getRecords());
        jsonResponse.setCount(rolePermissionPage.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }

}
