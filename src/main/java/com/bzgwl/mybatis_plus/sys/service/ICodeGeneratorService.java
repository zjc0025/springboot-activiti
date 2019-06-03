package com.bzgwl.mybatis_plus.sys.service;

import com.bzgwl.mybatis_plus.sys.entity.CodeGenerator;
import com.bzgwl.mybatis_plus.utils.JsonResponse;

public interface ICodeGeneratorService {

    /**
     * 分页查询
     * @param codeGenerator
     * @param page
     * @param limit
     * @return
     */
    JsonResponse findByParam(CodeGenerator codeGenerator, Integer page , Integer limit);
}
