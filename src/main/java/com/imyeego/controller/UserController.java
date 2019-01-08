package com.imyeego.controller;

import com.imyeego.pojo.BaseResult;
import com.imyeego.pojo.User;
import com.imyeego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{name}")
    public Object getUserByName(@PathVariable String name){
//        User user = new User(1L, name, "admin");
        User user = userService.login(name);
        if (user == null) return new BaseResult(200, "未找到相关用户");
        return user;
    }
}
