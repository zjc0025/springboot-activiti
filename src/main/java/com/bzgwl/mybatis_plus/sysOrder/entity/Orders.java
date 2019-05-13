package com.bzgwl.mybatis_plus.sysOrder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 代码生成器生成
* @Author Professor_Kong
* @Date   2019-05-10
* @Email  logwto@163.com
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @TableId(value = "orderid", type = IdType.AUTO)
    private Integer orderid;

    /**
    * 创建人
    */
    private String createor;

    /**
    * 创建时间
    */
    private LocalDateTime createtime;

    /**
    * 数量
    */
    private Long counts;

    /**
    * 订单名称
    */
    private String ordername;

    /**
    * 状态
    */
    private String status;


}
