package com.imyeego.controller;

import com.imyeego.pojo.BaseResult;
import com.imyeego.pojo.Process;
import com.imyeego.pojo.ProcessInfo;
import com.imyeego.pojo.User;
import com.imyeego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "{name}")
    public Object getUserByName(@PathVariable String name){
        User user = new User(1L, name, "admin");
//        User user = userService.login(name);
        if (user == null) return new BaseResult(200, "未找到相关用户");
        return user;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public User login(@RequestParam("username") String username, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("" );
        System.out.println("根目录:" + path);

        User user = userService.login(username);
        if (user == null) {
            user.setId(-1L);
            user.setUsername("");
            user.setPassword("");
        }
        return user;
    }



}
