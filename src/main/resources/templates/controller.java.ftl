package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
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
@SuppressWarnings("all")  //抑制各种黄线警告
@RequestMapping("/${package.ModuleName}/${entity ? lower_case}")
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


    /**
    * list跳转
    * @return
    */
    @RequestMapping("/mainIndex")
    public String mainIndex(){
        return "${entity ? uncap_first}/${entity ? uncap_first}_list";
    }

    /**
    * addOrUpdate 页面跳转
    * @param mv
    * @param ${entity ? uncap_first}
    * @return
    */
    @RequiresPermissions(value={"${entity ? uncap_first}:add", "${entity ? uncap_first}:update"}, logical= Logical.OR)
    @RequestMapping("/addOrUpdateIndex")
    public ModelAndView addOrUpdateIndex(ModelAndView mv ,${entity} ${entity ? uncap_first}){
        mv.setViewName("${entity ? uncap_first}/${entity ? uncap_first}_addOrUpdate");
        if(${entity ? uncap_first} != null){
            mv.addObject("obj",${entity ? uncap_first});
        }
        return mv;
    }

    /**
    * 根据条件 分页查询
    * @param ${entity ? uncap_first}
    * @param page
    * @param limit
    * @return
    */
<#if !restControllerStyle>
    @ResponseBody
</#if>
    @RequestMapping("/findByParams")
    public JsonResponse findByParams(${entity} ${entity ? uncap_first},Integer page , Integer limit){

        JsonResponse jsonResponse = ${table.serviceImplName ? uncap_first}.findByParam(${entity ? uncap_first}, page, limit);
        return jsonResponse;
    }

    /**
    * 新增or修改
    * @param ${entity ? uncap_first}
    * @return
    */
    @RequiresPermissions(value={"${entity ? uncap_first}:add", "${entity ? uncap_first}:update"}, logical= Logical.OR)
<#if !restControllerStyle>
    @ResponseBody
</#if>
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
    @RequiresPermissions("${entity ? uncap_first}:delete")
<#if !restControllerStyle>
    @ResponseBody
</#if>
    @RequestMapping("/delByIds")
    public JsonResponse delByIds(@RequestParam("ids[]") List<Integer> ids){
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
