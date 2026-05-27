package com.example.session12_ex4.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // @Before: log method name + args khi vào controller.
    @Before("execution(* com.example.session12_ex4.controller.StudentController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        log.info("[AOP @Before] Entering method '{}' with args: {}", methodName, args);
    }

    // @AfterThrowing: log exception message khi service ném exception.
    @AfterThrowing(pointcut = "execution(* com.example.session12_ex4.service.StudentService.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("[AOP @AfterThrowing] Exception thrown in service method '{}' - Message: {}", methodName, ex.getMessage());
    }

    // @Around: đo thời gian thực thi controller.
    @Around("execution(* com.example.session12_ex4.controller.StudentController.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("[AOP @Around] Method '{}' executed in {} ms", methodName, executionTime);
            return result;
        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.warn("[AOP @Around] Method '{}' failed in {} ms with exception: {}", methodName, executionTime, throwable.getMessage());
            throw throwable;
        }
    }
}
