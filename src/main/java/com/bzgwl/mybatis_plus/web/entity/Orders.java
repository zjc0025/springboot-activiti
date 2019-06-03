package com.bzgwl.mybatis_plus.web.entity;

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
* @Date   2019-05-29
* @Email  logwto@163.com
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 订单名称
    */
    private String orderName;

    /**
    * 订单类型
    */
    private String orderType;

    /**
    * 数量
    */
    private Integer count;

    /**
    * 订单号
    */
    private Integer orderNum;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 状态
    */
    private Integer status;


}
