package ru.sbrf.edu.banking.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Aspect
@Component
@Slf4j
public class BenchmarkAspect {

    @Pointcut("@annotation(ru.sbrf.edu.banking.annotation.Benchmark)")
    public void executionBenchmarkPointcut() {
    }

    @Around("executionBenchmarkPointcut()")
    public Object benchmark(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        LocalTime a = LocalTime.now();
        Object proceed = joinPoint.proceed();
        LocalTime b = LocalTime.now();
        Duration result = Duration.between(a, b);
        String stringResult = "sec: " + result.getSeconds() +
                " milis: " + result.getNano() / 1000000;
        log.info("Execution time of method: {} in class {} - {}",
                methodName,
                className,
                stringResult);
        return proceed;
    }
}
