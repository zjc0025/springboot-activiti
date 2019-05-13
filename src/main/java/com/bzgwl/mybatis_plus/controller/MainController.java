package com.bzgwl.mybatis_plus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/user")
    public String userList(){
        return "user/userCRUD";
    }
    @RequestMapping("/order")
    public String orderList(){
        return "orders/ordersCRUD";
    }
}
