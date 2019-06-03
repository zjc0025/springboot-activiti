package com.bzgwl.mybatis_plus.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CodeGenerator implements Serializable {

    public Integer id;
    public String name;
    public Integer type;
    public String tableName ;
    public String  dbName;
    public String  dbUsername;
    public String  dbPassword;
    public Date createTime;
    public Integer status;
    public String ip;
    public Integer port;

    public CodeGenerator(){
        this.createTime = new Date();
        this.status = 1;
    }

}
