package com.bzgwl.mybatis_plus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 代码生成器生成
* @Author Professor_Kong
* @Date   2019-05-21
* @Email  logwto@163.com
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 名称
    */
    private String name;

    /**
    * 描述
    */
    private String des;

    /**
    * 创建人
    */
    private String createuser;

    /**
    * 创建时间
    */
    private Date createtime;

    /**
    * 更新时间
    */
    private Date updatetime;

    /**
    * 状态
    */
    private String status;


}
