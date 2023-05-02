package com.foxminded.chendev.schoolconsoleapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class DaoLoggingAspect {

    private final Map<Class<?>, Logger> loggers = new ConcurrentHashMap<>();

    public void addLogger(Class<?> clazz, Logger logger) {
        loggers.put(clazz, logger);
    }

    private Logger getLogger(Class<?> clazz) {
        return loggers.computeIfAbsent(clazz, LoggerFactory::getLogger);
    }

    @Pointcut("execution(* com.foxminded.chendev.schoolconsoleapp.dao.impl.*.*(..))")
    public void daoMethods() {}

    @Before("daoMethods()")
    public void logBefore(JoinPoint joinPoint) {
        Logger logger = getLogger(joinPoint.getTarget().getClass());
        logger.info("Method {} is called with parameters: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "daoMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        Logger logger = getLogger(joinPoint.getTarget().getClass());
        logger.info("Method {} returned: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "daoMethods()", throwing = "DataBaseRuntimeException")
    public void logException(JoinPoint joinPoint) {
        Logger logger = getLogger(joinPoint.getTarget().getClass());
        logger.error("Exception in method {} with parameters: {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }
}
