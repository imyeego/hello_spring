package com.imyeego.controller;

import com.imyeego.component.JwtTokenUtil;
import com.imyeego.exception.ExpiredException;
import com.imyeego.pojo.TokenResponse;
import com.imyeego.pojo.User;
import com.imyeego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/accessToken/{userId}", method = RequestMethod.GET)
    public TokenResponse accessToken(@PathVariable long userId){
        System.out.println(tokenHeader);

        User user = userService.findById(userId);
        String token = null;
        if (user != null) {
            token = jwtTokenUtil.generateToken(user);
        }
        return new TokenResponse(token);
    }

    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    public TokenResponse refreshToken(HttpServletRequest request){
        String authToken = request.getHeader(tokenHeader);
        String token = null;
        if (authToken != null){
            token = jwtTokenUtil.refreshToken(authToken);
        } else {
            return new TokenResponse(null);
        }
        return new TokenResponse(token);
    }
}
