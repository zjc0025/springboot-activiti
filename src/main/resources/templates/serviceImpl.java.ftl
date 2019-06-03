package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
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
 * @Author ${author}
 * @Date   ${date}
 * @Email  ${cfg.email}
 */
@Service
@SuppressWarnings("all")  //抑制各种黄线警告
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    @Autowired(required = false)
    private ${table.mapperName} ${table.mapperName ? uncap_first};

    @Override
    public List<${entity}> list() {

       return ${table.mapperName ? uncap_first}.selectList(null);
    }

    /**
    * 分页查询
    *
    * @param ${entity ? uncap_first}  page  limit
    * @return jsonResponse
    */
    @Override
    public JsonResponse findByParam(${entity} ${entity ? uncap_first},Integer page , Integer limit) {

        JsonResponse jsonResponse = new JsonResponse();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 10 ;
        }
        //开启分页
        Page ${entity ? uncap_first}Page = new Page(page,limit);
        //查询构造器
        Wrapper wrapper = new QueryWrapper();

    <#list table.fields as field>
        if(${entity ? uncap_first}.get${field.propertyName ? cap_first}()!=null && !"".equals(${entity ? uncap_first}.get${field.propertyName ? cap_first}())){
            ((QueryWrapper) wrapper).eq("${field.name}",${entity ? uncap_first}.get${field.propertyName ? cap_first}());
        }
    </#list>
        IPage<${entity}> ${entity ? uncap_first}IPage = ${table.mapperName ? uncap_first}.selectPage(${entity ? uncap_first}Page, wrapper);

        jsonResponse.setData(${entity ? uncap_first}IPage.getRecords());
        jsonResponse.setCount(${entity ? uncap_first}Page.getTotal());
        jsonResponse.setMsg("ok");

        return jsonResponse;
    }

}
</#if>
