package com.zq.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {

    public static final Logger log = LoggerFactory.getLogger(ServiceLogAspect.class);
    /**
    *aop通知、
    * 前置通知
    * 后置通知
    * 环绕通知
    * 异常通知
    * 最终通知
     */

    /**
     * 切面表达式
     * execution执行方法主体
     * 第一处 * 代表方法返回类型
     * 第2处aop监控的类的范围
     * 第3处..代表该包下所有方法
     * 第4处* 代表类名
     * 第5处*（..）方法名和参数
     */

    @Around("execution(* com.zq.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("============  开始执行 {}.{}  ===========",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        long begin = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long takeTime  = end-begin;

        if (takeTime>3000){
            log.info("============  执行结束，耗时： {}毫秒  ===========",takeTime);
        }else if (takeTime>2000&&takeTime<3000){
            log.info("============  执行结束，耗时： {}毫秒  ===========",takeTime);
        }else if (takeTime>1000&&takeTime<2000){
            log.info("============  执行结束，耗时： {}毫秒  ===========",takeTime);
        }else if (takeTime<1000){
            log.info("============  执行结束，耗时： {}毫秒  ===========",takeTime);
        }


        return result;
    }
}
