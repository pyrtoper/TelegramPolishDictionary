package com.pyrtoper.dictionary.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {

    @Pointcut("execution(* com.pyrtoper.dictionary.dao.*.*(..))")
    public void forDAOPackage() {}

    @Pointcut("execution(* com.pyrtoper.dictionary.service.*.*(..))")
    public void forServicePackage() {}

    @Pointcut("execution(* com.pyrtoper.dictionary.controller.*.*(..))")
    public void forControllerPackage() {}

}
