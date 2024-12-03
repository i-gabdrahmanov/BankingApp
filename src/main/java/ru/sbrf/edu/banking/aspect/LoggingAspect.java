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
        Class<?> clazz = joinPoint.getThis().getClass();

        // Проверяем, является ли класс прокси от CGLIB
        if (clazz.getName().contains("$$")) {
            System.out.println("Используется CGLIB прокси");
        }
        // Проверяем, является ли класс JDK динамическим прокси
        else if (java.lang.reflect.Proxy.isProxyClass(clazz)) {
            System.out.println("Используется JDK динамическое прокси");
        } else {
            System.out.println("Неизвестный тип прокси");
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ExecutionLogger executionLogger = method.getAnnotation(ExecutionLogger.class) == null
                ? joinPoint.getTarget().getClass().getAnnotation(ExecutionLogger.class)
                : method.getAnnotation(ExecutionLogger.class);
        if (executionLogger != null && !executionLogger.exclude()) {
            log.info("Вызван метод: {} в классе: {} с параметрами: {}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getThis().getClass().getName(),
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
