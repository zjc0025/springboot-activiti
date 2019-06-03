package com.bzgwl.mybatis_plus.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 用户名
    */
    private String username;

    /**
    * 昵称
    */
    private String name;

    /**
    * 密码
    */
    private String password;

    /**
    * 状态
    */
    private Integer status;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 电话
    */
    private String phone;

    /**
    * 创建时间
    */
        @TableField("createTime")
    private Date createTime;

    /**
    * 登陆次数
    */
    private Integer logins;


}
