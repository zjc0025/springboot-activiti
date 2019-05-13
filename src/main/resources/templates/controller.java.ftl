package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 *  代码生成器
 *  前端控制器
 * @Author ${author}
 * @Date   ${date}
 * @Email  ${cfg.email}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${table.serviceName}  ${table.serviceImplName ? uncap_first};


    @RequestMapping("/findAll")
    public JsonResponse findAll(){
        JsonResponse jsonResponse = new JsonResponse();
        List<${table.name ? cap_first}> list = ${table.serviceImplName ? uncap_first}.list();

        if(list != null){
            jsonResponse.setData(list);
        }
        return jsonResponse;
    }

    /**
    * 根据条件 分页查询
    * @param ${entity ? uncap_first}
    * @param page
    * @param limit
    * @return
    */
    @RequestMapping("/findByParam")
    public JsonResponse findByParam(${entity} ${entity ? uncap_first},Integer page , Integer limit){

        JsonResponse jsonResponse = ${table.serviceImplName ? uncap_first}.findByParam(${entity ? uncap_first}, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param ${entity ? uncap_first}
    * @return
    */
    @RequestMapping("/addOrUpdate")
    public JsonResponse addOrUpdate(${entity} ${entity ? uncap_first}){
        JsonResponse jsonResponse = new JsonResponse();
        try {
            ${table.serviceImplName ? uncap_first}.saveOrUpdate(${entity ? uncap_first});
        }catch (Exception e){
            jsonResponse.setCode("1");
            e.printStackTrace();
        }
        return jsonResponse;
    }

    /**
    * 删除
    * @param ids
    * @return
    */
    @RequestMapping("delByIds")
    public JsonResponse delByIds(@RequestParam("ids[]") List<Long> ids){
        JsonResponse jsonResponse = new JsonResponse();

        try {
            ${table.serviceImplName ? uncap_first}.removeByIds(ids);
        }catch (Exception e){
            jsonResponse.setCode("1");
            e.printStackTrace();
        }

        return jsonResponse;
    }

 }
</#if>
