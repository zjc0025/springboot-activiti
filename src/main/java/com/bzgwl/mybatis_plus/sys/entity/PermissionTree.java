package com.bzgwl.mybatis_plus.sys.entity;

import lombok.Data;

@Data
public class PermissionTree {

//     "authorityId": 1,
//             "authorityName": "系统管理",
//             "orderNumber": 1,
//             "menuUrl": null,
//             "menuIcon": "layui-icon-set",
//             "createTime": "2018/06/29 11:05:41",
//             "authority": null,
//             "checked": 0,
//             "updateTime": "2018/07/13 09:13:42",
//             "isMenu": 0,
//             "parentId": -1

    private Integer authorityId;
    private String authorityName;
    private String authority;
    private Integer orderNumber;
    private String menuUrl;
    private Integer checked;
    private Integer isMenu;
    private Integer parentId;

    public PermissionTree() {
        this.isMenu = 1;    // 1 菜单  -1 主菜单  0 按钮
        this.checked = 0;   // 0 展开 1 折叠
    }
}
