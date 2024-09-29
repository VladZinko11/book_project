package com.zinko.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * com.zinko.service.impl..*(..))")
    private void publicMethodsFromService() {
    }


    @Before(value = "publicMethodsFromService()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        log.info(">> {}() - {}", name, Arrays.toString(args));
    }

    @AfterReturning(value = "publicMethodsFromService()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String name = joinPoint.getSignature().getName();
        log.info("<< {}() - {}", name, result);
    }

    @AfterThrowing(pointcut = "publicMethodsFromService()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String name = joinPoint.getSignature().getName();
        log.error("<< {}() - {}", name, exception.getMessage());
    }

}
