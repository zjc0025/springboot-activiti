package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;

/**
 * 代码生成器
 * Mapper 接口
 * @Author ${author}
 * @Date   ${date}
 * @Email  ${cfg.email}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
