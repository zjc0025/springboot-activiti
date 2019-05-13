package com.bzgwl.mybatis_plus.sysUser.service.impl;

import com.bzgwl.mybatis_plus.sysUser.entity.User;
import com.bzgwl.mybatis_plus.sysUser.mapper.UserMapper;
import com.bzgwl.mybatis_plus.sysUser.service.IUserService;
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
 * @Date   2019-05-09
 * @Email  logwto@163.com
 */
@Service
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
        if(user.getPassword()!=null && !"".equals(user.getPassword())){
            ((QueryWrapper) wrapper).eq("password",user.getPassword());
        }
        if(user.getAge()!=null && !"".equals(user.getAge())){
            ((QueryWrapper) wrapper).eq("age",user.getAge());
        }
        if(user.getSource()!=null && !"".equals(user.getSource())){
            ((QueryWrapper) wrapper).eq("source",user.getSource());
        }
        if(user.getEmail()!=null && !"".equals(user.getEmail())){
            ((QueryWrapper) wrapper).eq("email",user.getEmail());
        }
        IPage<User> userIPage = userMapper.selectPage(userPage, wrapper);

        jsonResponse.setData(userIPage.getRecords());
        jsonResponse.setCount(userPage.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }

}
