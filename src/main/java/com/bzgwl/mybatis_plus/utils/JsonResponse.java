package com.bzgwl.mybatis_plus.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//@Data
public class JsonResponse {

    private String msg;
    private String code;
    private List  Data;
    private Long count;


    public JsonResponse() {
        this.msg = "操作成功！";
        this.code = "0";   //layUi 返回正确响应code 为0
        Data = new ArrayList();
        this.count = 0L;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List getData() {
        return Data;
    }

    public void setData(List data) {
        Data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
