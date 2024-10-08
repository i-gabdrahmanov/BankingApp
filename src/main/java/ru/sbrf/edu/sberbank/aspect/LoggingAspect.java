package ru.sbrf.edu.sberbank.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("@annotation(ru.sbrf.edu.sberbank.annotation.ExecutionLogger)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Вызван метод " + joinPoint.getSignature().getName() +
                " в классе " + joinPoint.getThis().getClass().getTypeName() +
                " с параметрами " + Arrays.toString(joinPoint.getArgs()));
    }

    @After(value = "@annotation(ru.sbrf.edu.sberbank.annotation.ExecutionLogger)")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Exiting method: " + joinPoint.getSignature().getName());
    }
}
