package com.example.restoranvoting.util.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * com.example.restoranvoting.web..*(..))")
    public void MethodsFromWebPackage() {
    }

    @Before("MethodsFromWebPackage()")
    public void test(JoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        log.info("-".repeat(103) + "\n" + methodSignature.getDeclaringTypeName()
                + " method: " + methodSignature.getMethod().getName()
                + ", with parameters: " + Arrays.toString(methodSignature.getParameterTypes()) + Arrays.toString(jp.getArgs()) + "\n");
    }
}
