package ru.sbrf.edu.banking.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
@Slf4j
public class RepoLoggingAspect {

    @Pointcut("execution(* ru.sbrf.edu.banking.repository..*(..))")
    void repositoryPointcut() {
    }

    @Around("repositoryPointcut()")
    public Object repositoryQueryCheckup(ProceedingJoinPoint joinPoint) {
        Object result;
        int collectionSize = 0;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        Class<?> returnType = methodSignature.getReturnType();
        if (returnType.isAssignableFrom(Collection.class)) {
            for (Object o : (Collection) result) {
                collectionSize++;
                log.info("Метод {} вызван с параметрами {}, вернул коллекцию размером {} элементов",
                       methodName, Arrays.toString(args), collectionSize);
            }
        }
        return result;
    }
}
