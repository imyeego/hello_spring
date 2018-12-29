package com.imyeego.aspect;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private long startTimeMillis = 0; // 开始时间
    private long endTimeMillis = 0; // 结束时间

    @Pointcut("within(com.imyeego.controller..*)")
    public void pointCut(){

    }

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint){
        String method = joinPoint.getSignature().getName();
        System.out.println("---------" + method + "-------------");
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
