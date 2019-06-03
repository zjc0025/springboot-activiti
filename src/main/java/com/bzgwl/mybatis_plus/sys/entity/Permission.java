package com.bzgwl.mybatis_plus.sys.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 菜单名称
    */
    private String authName;

    /**
    * 排序
    */
    private Integer orderNum;

    /**
    * 菜单URL
    */
    private String menuUrl;

    /**
    * icon
    */
    private String menuIcon;

    /**
    * 权限标志
    */
    private String auth;

    /**
    * 是否折叠  0展开 1折叠   
    */
    private Integer checked;

    /**
    * 是否菜单  0是  1 按钮  -1目录
    */
    private Integer isMenu;

    /**
    * 父级菜单ID
    */
    private Integer parentId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改数据
    */
    private Date updateTime;

    /**
    * 创建人
    */
    private Integer createUser;

    /**
    * 状态 0禁言  1启用
    */
    private String status;

    public Permission() {
        this.setChecked(0);
        this.setCreateTime(new Date());
        this.setUpdateTime(new Date());
        this.setStatus("1");
        this.setCreateUser(3);
    }
}
