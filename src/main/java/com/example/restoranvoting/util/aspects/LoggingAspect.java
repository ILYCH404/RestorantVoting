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

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void DeleteMapping(){}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void PutMapping(){}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void PostMapping(){}

    @Pointcut("execution(public * com.example.restoranvoting.web..get*(..))")
    public void GetMapping(){}

    @Before("DeleteMapping() || PostMapping() || PutMapping() || GetMapping() || execution(* enable(int, boolean))")
    public void MappingMethods(JoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        log.info("-".repeat(103));
        log.info(methodSignature.getDeclaringTypeName()
                + " method: " + methodSignature.getMethod().getName()
                + ", with parameters: " + Arrays.toString(methodSignature.getParameterTypes()) + Arrays.toString(jp.getArgs()));
    }
}
