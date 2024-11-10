package ru.sbrf.edu.banking.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionLogger {

    @Pointcut("@annotation(ru.sbrf.edu.banking.annotation.ExceptionAudit)")
    void exceptionPointcut() {
    }

    @AfterThrowing(value = "exceptionPointcut()", throwing = "ex")
    void logException(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.error("Exception in class {} in method: {} with message {}",
                className, methodName, ex.getMessage());
    }
}
