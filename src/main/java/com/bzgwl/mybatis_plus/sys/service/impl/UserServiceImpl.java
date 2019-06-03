package com.bzgwl.mybatis_plus.sys.service.impl;

import com.bzgwl.mybatis_plus.sys.entity.User;
import com.bzgwl.mybatis_plus.sys.mapper.UserMapper;
import com.bzgwl.mybatis_plus.sys.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public List<User> list() {

       return userMapper.selectList(null);
    }

    /**
    * 分页查询
    *
    * @param user  page  limit
    * @return jsonResponse
    */
    @Override
    public JsonResponse findByParam(User user,Integer page , Integer limit) {

        JsonResponse jsonResponse = new JsonResponse();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10 ;
        }
        //开启分页
        Page userPage = new Page(page,limit);
        //查询构造器
        Wrapper wrapper = new QueryWrapper();

        if(user.getId()!=null && !"".equals(user.getId())){
            ((QueryWrapper) wrapper).eq("id",user.getId());
        }
        if(user.getUsername()!=null && !"".equals(user.getUsername())){
            ((QueryWrapper) wrapper).eq("username",user.getUsername());
        }
        if(user.getName()!=null && !"".equals(user.getName())){
            ((QueryWrapper) wrapper).eq("name",user.getName());
        }
        if(user.getPassword()!=null && !"".equals(user.getPassword())){
            ((QueryWrapper) wrapper).eq("password",user.getPassword());
        }
        if(user.getStatus()!=null && !"".equals(user.getStatus())){
            ((QueryWrapper) wrapper).eq("status",user.getStatus());
        }
        if(user.getEmail()!=null && !"".equals(user.getEmail())){
            ((QueryWrapper) wrapper).eq("email",user.getEmail());
        }
        if(user.getPhone()!=null && !"".equals(user.getPhone())){
            ((QueryWrapper) wrapper).eq("phone",user.getPhone());
        }
        if(user.getCreateTime()!=null && !"".equals(user.getCreateTime())){
            ((QueryWrapper) wrapper).eq("createTime",user.getCreateTime());
        }
        if(user.getLogins()!=null && !"".equals(user.getLogins())){
            ((QueryWrapper) wrapper).eq("logins",user.getLogins());
        }
        IPage<User> userIPage = userMapper.selectPage(userPage, wrapper);

        jsonResponse.setData(userIPage.getRecords());
        jsonResponse.setCount(userPage.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }

}
