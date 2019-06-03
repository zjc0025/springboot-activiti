package com.bzgwl.mybatis_plus.sys.service.impl;

import com.bzgwl.mybatis_plus.sys.entity.*;
import com.bzgwl.mybatis_plus.sys.mapper.PermissionMapper;
import com.bzgwl.mybatis_plus.sys.mapper.RolePermissionMapper;
import com.bzgwl.mybatis_plus.sys.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  代码生成器
 *  服务实现类
 * @Author Professor_Kong
 * @Date   2019-05-21
 * @Email  logwto@163.com
 */
@Service
@SuppressWarnings("all")  //抑制各种黄线警告
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    @Autowired(required = false)
    private RolePermissionMapper rolePermissionMapper;



    @Override
    public List<Permission> list() {

       return permissionMapper.selectList(null);
    }

    /**
    * 分页查询
    *
    * @param permission  page  limit
    * @return jsonResponse
    */
    @Override
    public JsonResponse findByParam(Permission permission,Integer page , Integer limit) {

        JsonResponse jsonResponse = new JsonResponse();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10 ;
        }
        //开启分页
        Page permissionPage = new Page(page,limit);
        //查询构造器
        Wrapper wrapper = new QueryWrapper();

        if(permission.getId()!=null && !"".equals(permission.getId())){
            ((QueryWrapper) wrapper).eq("id",permission.getId());
        }
        if(permission.getAuthName()!=null && !"".equals(permission.getAuthName())){
            ((QueryWrapper) wrapper).eq("auth_name",permission.getAuthName());
        }
        if(permission.getOrderNum()!=null && !"".equals(permission.getOrderNum())){
            ((QueryWrapper) wrapper).eq("order_num",permission.getOrderNum());
        }
        if(permission.getMenuUrl()!=null && !"".equals(permission.getMenuUrl())){
            ((QueryWrapper) wrapper).eq("menu_url",permission.getMenuUrl());
        }
        if(permission.getMenuIcon()!=null && !"".equals(permission.getMenuIcon())){
            ((QueryWrapper) wrapper).eq("menu_icon",permission.getMenuIcon());
        }
        if(permission.getAuth()!=null && !"".equals(permission.getAuth())){
            ((QueryWrapper) wrapper).eq("auth",permission.getAuth());
        }
        if(permission.getChecked()!=null && !"".equals(permission.getChecked())){
            ((QueryWrapper) wrapper).eq("checked",permission.getChecked());
        }
        if(permission.getIsMenu()!=null && !"".equals(permission.getIsMenu())){
            ((QueryWrapper) wrapper).eq("is_menu",permission.getIsMenu());
        }
        if(permission.getParentId()!=null && !"".equals(permission.getParentId())){
            ((QueryWrapper) wrapper).eq("parent_id",permission.getParentId());
        }
        if(permission.getCreateTime()!=null && !"".equals(permission.getCreateTime())){
            ((QueryWrapper) wrapper).eq("create_time",permission.getCreateTime());
        }
        if(permission.getUpdateTime()!=null && !"".equals(permission.getUpdateTime())){
            ((QueryWrapper) wrapper).eq("update_time",permission.getUpdateTime());
        }
        if(permission.getCreateUser()!=null && !"".equals(permission.getCreateUser())){
            ((QueryWrapper) wrapper).eq("create_user",permission.getCreateUser());
        }
        if(permission.getStatus()!=null && !"".equals(permission.getStatus())){
            ((QueryWrapper) wrapper).eq("status",permission.getStatus());
        }
        IPage<Permission> permissionIPage = permissionMapper.selectPage(permissionPage, wrapper);

        jsonResponse.setData(permissionIPage.getRecords());
        jsonResponse.setCount(permissionPage.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }

    /**
     * 查询用户菜单
     *
     * @param user
     * @return
     */
    @Override
    public List<Menu> getMenuByUser(User user) {
        // 创建菜单对象
        List<Menu> menus = new ArrayList<>();
        //一级菜单
        List<Permission> firstMenu = permissionMapper.findFirstMenu(user.getId());
        //转化 菜单
        firstMenu.forEach(p ->{
            Menu menu1 = new Menu();
            menu1.setId(p.getId());
            menu1.setTitle(p.getAuthName());
            menu1.setHref(p.getMenuUrl());
            menu1.setIcon(p.getMenuIcon());
            menus.add(menu1);
        });
        //二级菜单
        menus.forEach( s ->{
            List<Menu> list = new ArrayList<>();
            List<Permission> secondMenu = permissionMapper.findSecondMenu(user.getId(), s.getId());

            secondMenu.forEach( se ->{
                Menu menu2 = new Menu();
                menu2.setId(se.getId());
                menu2.setTitle(se.getAuthName());
                menu2.setHref(se.getMenuUrl());
                menu2.setIcon(se.getMenuIcon());
                list.add(menu2);
            });
            //一级菜单子菜单
            s.setChildren(list);
        });
        return menus;
    }

    /**
     * 查询所有权限  当前角色权限选中
     * @param user
     * @return
     */
    @Override
    public List<ZNode> getPermissionByRoleId(Integer id) {

        //角色的权限
        List<Permission> userPermission = permissionMapper.findPermissionByRid(id);

        //所有权限
        Wrapper<Permission> wrapper = new QueryWrapper();
        //注意  此处的 comlum 为 数据库字段 ，而非实体类
        ((QueryWrapper<Permission>) wrapper).ne("parent_id",-1);
        ((QueryWrapper<Permission>) wrapper).orderByAsc("order_num");
        List<Permission> permissions = permissionMapper.selectList(wrapper);

        List<ZNode> list = new ArrayList<>();
        permissions.forEach( m ->{

            ZNode zNode = new ZNode();
            zNode.setId(m.getId());
            zNode.setName(m.getAuthName());
            zNode.setPId(m.getParentId());
            for(Permission p : userPermission){
                if(m.getId() == p.getId()){
                    zNode.setChecked(true);
                    break;
                }
            }
            list.add(zNode);
        });

        return list;
    }

    /**
     * 维护 角色 权限关系表
     *
     * @param rid
     * @param pids
     */
    @Override
    public void upRolePmLink(Integer rid, List<Integer> pids) {

        Wrapper wrapper = new QueryWrapper();
        ((QueryWrapper) wrapper).eq("r_id",rid);
        rolePermissionMapper.delete(wrapper);

        if(pids != null && pids.size()>0){
            pids.forEach(p ->{
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRId(rid);
                rolePermission.setPId(p);
                rolePermission.setStatus("1");
                rolePermissionMapper.insert(rolePermission);
            });
        }
    }

    /**
     * 获取全部一级 或 二级菜单
     * @return
     */
    @Override
    public List<Permission> getPByLevel(Integer level) {
        Wrapper wrapper = new QueryWrapper();
        if(level ==2){
            level=1;
        }
        if(level ==0){
            level=2;
        }
        ((QueryWrapper) wrapper).eq("is_menu",level);
        List list = permissionMapper.selectList(wrapper);
        return list;
    }

    /**
     * 删除权限 如果是一级则 删除下级 及按钮 ，同时删除 角色权限相关 数据
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        Wrapper wrapper = new QueryWrapper();
        ((QueryWrapper) wrapper).eq("id",id);
        Permission permission = permissionMapper.selectOne(wrapper);

        Set<Permission> needDel = new HashSet<>();

        if(permission!=null){
            Integer isMenu = permission.getIsMenu();

            needDel.add(permission);
            //一级菜单
            if(isMenu == 1){
                Wrapper wrapper2 = new QueryWrapper();
                ((QueryWrapper) wrapper2).eq("parent_id",permission.getId());
                List<Permission> list = permissionMapper.selectList(wrapper2);

                //二级菜单
                if(list !=null && list.size()>0){
                    for(Permission p : list){
                        Wrapper wrapper3 = new QueryWrapper();
                        ((QueryWrapper) wrapper3).eq("parent_id",p.getId());
                        List<Permission> list1 = permissionMapper.selectList(wrapper3);
                        needDel.addAll(list1); //按钮
                    }
                    needDel.addAll(list); //二级菜单
                }
            }
            //二级菜单
            if(isMenu == 2){
                Wrapper wrapper5 = new QueryWrapper();
                ((QueryWrapper) wrapper5).eq("parent_id",permission.getId());
                List<Permission> list3 = permissionMapper.selectList(wrapper5);
                if(list3 !=null && list3.size()> 0){
                    needDel.addAll(list3);
                }
            }
        }

        List<Integer> ids = new ArrayList<>();
        for(Permission ps : needDel){
            ids.add(ps.getId());
        }

        //删除 权限信息表数据
        permissionMapper.deleteBatchIds(ids);

        Wrapper wrapper4 = new QueryWrapper();
        ((QueryWrapper) wrapper4).in("p_id",ids);
        List<RolePermission> selectList = rolePermissionMapper.selectList(wrapper4);

        ids.clear(); //清空
        for(RolePermission rolePermission : selectList){
            ids.add(rolePermission.getId());
        }
        //删除 角色权限关系表数据
        rolePermissionMapper.deleteBatchIds(ids);


    }
}
