package com.imyeego.controller;

import com.imyeego.pojo.BaseResult;
import com.imyeego.websocket.CommonWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/websocket")
public class WebsocketController {

    @Bean
    public CommonWebSocketHandler websocket() {
        return CommonWebSocketHandler.instance();
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public BaseResult sendToUsers(@RequestParam("userid") String userid, @RequestParam("message") String message) {
        if ("-1".equals(userid)) websocket().sendToUsers(message);
        else websocket().sendToUser(userid, message);
        return new BaseResult(200, "success");
    }
}
