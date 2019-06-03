package com.bzgwl.mybatis_plus.sys.entity;

import lombok.Data;

/**
 * 角色  赋权  ZTree实体类
 */
@Data
public class ZNode {

    private Integer id;
    private Integer pId;
    private String name;
    private boolean checked;
    private boolean open;

    public ZNode() {
        this.checked = false;
        this.open = true;
    }
}
