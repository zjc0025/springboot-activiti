package com.bzgwl.mybatis_plus.sys.entity;

import lombok.Data;

import java.util.List;

/**
 * 菜单 实体类
 */
@Data
public class Menu {

    private Integer id;  //
    private Integer pId;    //父ID
    private String title;   //标题
    private String icon;    //图标
    private String href;    //链接  请求地址
    private boolean isClose;  //是否 关闭
    private boolean spread;     //是否折叠
    private boolean isCheck;    //是否 选中
    private List<Menu> children;

    public Menu() {
        this.spread = false;
        this.isCheck = false;
        this.isClose = false;
    }
}
