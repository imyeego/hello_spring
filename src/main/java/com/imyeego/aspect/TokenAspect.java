package com.imyeego.aspect;

import com.alibaba.fastjson.JSON;
import com.imyeego.component.JwtTokenUtil;
import com.imyeego.exception.ExpiredException;
import com.imyeego.exception.MatchException;
import com.imyeego.pojo.BaseResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@Aspect
//@Component
public class TokenAspect {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Pointcut("within(com.imyeego.controller..*) && !within(com.imyeego.controller.TokenController)")
    public void pointCut(){

    }

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object result = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        HttpServletResponse response = attributes.getResponse();
        String authToken = request.getHeader(tokenHeader);
        if (authToken == null) {
            response.setStatus(401);
            result = new BaseResult(401, "未通过验证");
        } else{
            String pathVariable = (String) joinPoint.getArgs()[0];
            try {
                if (jwtTokenUtil.validateToken(authToken, pathVariable)){
                    result = joinPoint.proceed();
                }
            } catch (ExpiredException e) {
                response.setStatus(401);
                result = new BaseResult(401, e.getMessage());
            } catch (MatchException e) {
                response.setStatus(401);
                result = new BaseResult(401, e.getMessage());
            } catch (Throwable e){
                e.printStackTrace();
            }

        }

        return result;
    }
}
