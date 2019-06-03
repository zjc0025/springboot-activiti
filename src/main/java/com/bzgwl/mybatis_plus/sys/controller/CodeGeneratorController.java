package com.bzgwl.mybatis_plus.sys.controller;

import com.bzgwl.mybatis_plus.sys.entity.CodeGenerator;
import com.bzgwl.mybatis_plus.sys.service.ICodeGeneratorService;
import com.bzgwl.mybatis_plus.utils.DBUtils;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.util.ArrayList;

import static com.bzgwl.mybatis_plus.utils.DBUtils.mySqlDriver;

@Controller
@RequestMapping("/sys/codeGenerator/")
public class CodeGeneratorController {

    @Autowired
    private ICodeGeneratorService codeGeneratorService;

    @RequestMapping("mainIndex")
    public String mainIndex(){
        return "codeGenerator/codeGeneratorIndex";
    }


    /**
     * 根据条件 分页查询
     * @param codeGenerator
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByParams")
    public JsonResponse findByParams(CodeGenerator codeGenerator, Integer page , Integer limit){

        JsonResponse jsonResponse = codeGeneratorService.findByParam(codeGenerator, page, limit);
        return jsonResponse;
    }

    @RequestMapping("/executingForm")
    public String addOrUpdateIndex(){
        return "codeGenerator/executingForm";
    }

    @RequestMapping("/testConnection")
    public JsonResponse testConnection(CodeGenerator codeGenerator){
        JsonResponse jp = new JsonResponse();

        return  jp;
    }

    @RequestMapping("/getTableNames")
    @ResponseBody
    public JsonResponse getTableNames(CodeGenerator cg){
        JsonResponse jp = new JsonResponse();

        ArrayList<String> tableNames = new ArrayList<>();
        DBUtils dbUtils = new DBUtils();
        try {
            if (cg != null) {
                StringBuffer url = new StringBuffer("jdbc:mysql://localhost:3306/");
                String username = cg.getDbUsername();
                String password = cg.getDbPassword();

                if (cg.getType() != null && cg.getType() == 1) {
                    url.append(cg.getDbName()).append("?serverTimezone=GMT%2B8&useSSL=false");
                    Connection connection = dbUtils.getConnection(mySqlDriver, url.toString(), username, password);
                    tableNames = dbUtils.getTableNames(connection);
                    jp.setData(tableNames);
                }
            }
        }catch (Exception e){
            jp.setMsg("获取数据库表失败！！");
            e.printStackTrace();
        }
        return  jp;
    }
}
