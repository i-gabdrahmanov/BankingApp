package ru.sbrf.edu.sberbank.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Aspect
@Component
public class BenchmarkAspect {

    @Pointcut("@annotation(ru.sbrf.edu.sberbank.annotation.Benchmark)")
    public void executionBenchmarkPointcut() {
    }

    @Around("executionBenchmarkPointcut()")
    public Object benchmark(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalTime a = LocalTime.now();
        Object proceed = joinPoint.proceed();
        LocalTime b = LocalTime.now();
        Duration result = Duration.between(a, b);
        String stringResult = "sec: " + result.getSeconds() + " milis: " + result.getNano()/1000000;
        System.out.println("Execution time of method " +
                joinPoint.getSignature().getName() +
                ": " +
                "in class: " + joinPoint.getTarget().getClass().getSimpleName() +
                " " +
                stringResult);
        return proceed;
    }
}
