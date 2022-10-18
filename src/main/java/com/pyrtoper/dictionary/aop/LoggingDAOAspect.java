package com.pyrtoper.dictionary.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingDAOAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingDAOAspect.class.getName());


    @After("execution(* setWorkState(..))")
    public void afterChangingWorkState(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info("Polish To Russian work state of a bot was changed to " + args[0]);
    }

    @Around("com.pyrtoper.dictionary.aop.Pointcuts.forRepositoryPackage() || "
        + "com.pyrtoper.dictionary.aop.Pointcuts.forServicePackage() || "
        + "com.pyrtoper.dictionary.aop.Pointcuts.forHandlersPackage() || "
        + "com.pyrtoper.dictionary.aop.Pointcuts.forControllerPackage()")
    public Object loggingDAOMethodArgs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().toShortString();
        try {
            long begin = System.currentTimeMillis();
            Object result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.info("Method: " + methodName + " args: " + Arrays.toString(
                proceedingJoinPoint.getArgs()) + " duration: " + (end - begin) + " ms");
            return result;
        } catch (Exception e) {
            logger.error("Method: " + methodName + " " + e.getMessage());
            throw e;
        }
    }
}
