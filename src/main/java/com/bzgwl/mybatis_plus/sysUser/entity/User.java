package com.bzgwl.mybatis_plus.sysUser.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* 代码生成器生成
* @Author Professor_Kong
* @Date   2019-05-09
* @Email  logwto@163.com
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 姓名
    */
    private String username;

    /**
    * 密码
    */
    private String password;

    /**
    * 年龄
    */
    private String age;

    /**
    * 分数
    */
    private Integer source;

    /**
    * 邮箱
    */
    private String email;


}
