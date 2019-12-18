package com.bzgwl.mybatis_plus.web.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class QingjForm implements Serializable {

    private static final long serialVersionUID = -8667789393679247550L;

    private String contents;
    private Integer days;
    private String username;
    private String types;
    private String key;
}
