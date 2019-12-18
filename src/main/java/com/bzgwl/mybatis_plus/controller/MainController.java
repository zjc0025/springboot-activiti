package com.bzgwl.mybatis_plus.controller;

import com.bzgwl.mybatis_plus.sys.entity.User;
import com.bzgwl.mybatis_plus.utils.JsonResponse;
import com.sun.deploy.net.HttpResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    // 登陆页面跳转
    @RequestMapping({"/","login"})
    public String login(){
        return "login";
    }

    // 首页跳转
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    //home
    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    //字体菜单跳转
    @RequestMapping("/okfont")
    public String okfont(){
        return "other/okfont";
    }

    @RequestMapping("/user")
    public String userList(){
        return "user/userCRUD";
    }
    @RequestMapping("/order")
    public String orderList(){
        return "orders/ordersCRUD";
    }


    @RequestMapping("/500")
    public String goTo500(){
        return "500";
    }
    @RequestMapping("/404")
    public String goTo404(){
        return "404";
    }
    @RequestMapping("/403")
    public String goTo403(){
        return "403";
    }


    //登陆
    @RequestMapping("/loginIn")
    @ResponseBody
    public JsonResponse  loginIn(String username, String password) {
        JsonResponse jsonResponse = new JsonResponse();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            String simpleName = e.getClass().getSimpleName();
            if ("UnknownAccountException".equals(simpleName)) {
//                map.put("msg", "用户不存在");
                jsonResponse.setMsg("用户不存在");
                jsonResponse.setCode("-1");
                return jsonResponse;
            } else if("IncorrectCredentialsException".equals(simpleName)){
//                map.put("msg", "密码不正确");
                jsonResponse.setMsg("密码不正确");
                jsonResponse.setCode("-2");
                return jsonResponse;
            }
        }
        boolean authenticated = subject.isAuthenticated();
        if (authenticated) {
            jsonResponse.setMsg("登陆成功！");
            return jsonResponse;
        }
        jsonResponse.setCode("-3");
        jsonResponse.setMsg("认证失败！");
        return jsonResponse;
    }


    @RequestMapping("/article")
    public String article(){
        return "article/article";
    }

    @RequestMapping("getLoginUserName")
    @ResponseBody
    public JsonResponse getLoginUserName(){
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        JsonResponse j = new JsonResponse();
        List<String> list = new ArrayList<>();
        list.add(user.getName());
        j.setData(list);
        return j;
    }
}
