package com.personal.test.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public final class DemoAop {

    private static final Logger LOG = LoggerFactory.getLogger(DemoAop.class);

    @Pointcut("execution(* com.personal.test.demo.*.dao.*.*(..))")
    public void dao() {
    }

    @Pointcut("execution(* com.personal.test.demo.web.*.*(..))")
    public void controller() {
    }

    @Before("controller()")
    public void beforeController(final JoinPoint joinPoint) {
        LOG.info("Before: " + joinPoint.getSignature().getName());
    }

    @Before("dao()")
    public void before(final JoinPoint joinPoint) {
        LOG.info("Before: " + joinPoint.getSignature().getName());
    }

    @After("dao()")
    public void after(final JoinPoint joinPoint) {
        LOG.info("After: " + joinPoint.getSignature().getName());
    }

    @Around("dao()")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        LOG.info("Around start: " + pjp.getSignature().getName());
        Object rlt = pjp.proceed();
        LOG.info("Around end: " + pjp.getSignature().getName());
        return rlt;
    }
}
