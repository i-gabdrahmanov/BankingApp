package ru.sbrf.edu.banking.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.sbrf.edu.banking.annotation.ExecutionLogger;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("@within(ru.sbrf.edu.banking.annotation.ExecutionLogger) || " +
            "@annotation(ru.sbrf.edu.banking.annotation.ExecutionLogger)")
    public void executionLoggerPointcut() {
    }

    @Before("executionLoggerPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ExecutionLogger executionLogger = method.getAnnotation(ExecutionLogger.class);
        if (executionLogger != null && !executionLogger.exclude()) {
            log.info("Вызван метод: {} в классе: {} с параметрами: {}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getTarget().getClass().getSimpleName(),
                    Arrays.toString(joinPoint.getArgs()));
        }
    }

    @After(value = "executionLoggerPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ExecutionLogger executionLogger = method.getAnnotation(ExecutionLogger.class);
        if (executionLogger != null && !executionLogger.exclude()) {
            log.info("Завершение исполнения метода: {}", joinPoint.getSignature().getName());
        }
    }
}
