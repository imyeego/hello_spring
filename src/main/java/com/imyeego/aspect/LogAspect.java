package com.imyeego.aspect;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Aspect
//@Component
public class LogAspect {

    private long startTimeMillis = 0; // 开始时间
    private long endTimeMillis = 0; // 结束时间

    @Pointcut("within(com.imyeego.controller..*)")
    public void pointCut(){

    }

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint){
        Signature method = joinPoint.getSignature();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        System.out.println("---------" + method.getName() + ": "+ request.getRequestURI() + "-------------");
    }


    @AfterReturning(value = "pointCut()", returning = "ret")
    public void after(JoinPoint joinPoint, Object ret){
        String json = JSON.toJSONString(ret);
        System.out.println("---------->" + json + "-------------");

    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        startTimeMillis = System.currentTimeMillis();
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        endTimeMillis = System.currentTimeMillis();
        long seconds = endTimeMillis - startTimeMillis;
        System.out.println("----------" + seconds + "ms-------------");

        return result;

    }
}
