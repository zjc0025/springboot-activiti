package com.bzgwl.mybatis_plus.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bzgwl.mybatis_plus.sys.entity.CodeGenerator;
import com.bzgwl.mybatis_plus.sys.entity.User;
import com.bzgwl.mybatis_plus.sys.mapper.CodeGeneratorMapper;
import com.bzgwl.mybatis_plus.sys.service.ICodeGeneratorService;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")  //抑制各种黄线警告
public class CodeGeneratorServiceImpl  implements ICodeGeneratorService {


    @Autowired
    private CodeGeneratorMapper codeGeneratorMapper;

    /**
     * 分页查询
     *
     * @param codeGenerator
     * @param page
     * @param limit
     * @return
     */
    @Override
    public JsonResponse findByParam(CodeGenerator codeGenerator, Integer page, Integer limit) {

        JsonResponse jsonResponse = new JsonResponse();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10 ;
        }
        //开启分页
        Page page1 = new Page(page,limit);
        //查询构造器
        Wrapper wrapper = new QueryWrapper();
        if(codeGenerator.getName() !=null && !codeGenerator.getName().equals("")){
            ((QueryWrapper) wrapper).like("name",codeGenerator.getName());
        }

        IPage<User> userIPage = codeGeneratorMapper.selectPage(page1, wrapper);

        jsonResponse.setData(userIPage.getRecords());
        jsonResponse.setCount(page1.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }
}
