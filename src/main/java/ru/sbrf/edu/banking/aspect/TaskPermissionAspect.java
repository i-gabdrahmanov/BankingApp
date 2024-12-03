package ru.sbrf.edu.banking.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.sbrf.edu.banking.model.Department;
import ru.sbrf.edu.banking.service.PermissionService;

import java.util.ArrayList;

@Aspect
@RequiredArgsConstructor
@Component
public class TaskPermissionAspect {

    private final PermissionService permissionService;

    @Pointcut("execution(* generateDailyTask*(..))")
    private void dailyTaskPermissionPointcut() {
    }

    @Around("dailyTaskPermissionPointcut()")
    private Object processDailyTaskPermission(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args[0] instanceof ArrayList<?> && args[1] instanceof Department) {
            args[0] = permissionService.getPermissionFor((Department) args[1]);
            try {
                return joinPoint.proceed(args);
            } catch (Throwable e) {
                throw new RuntimeException("Ошибка обработки аспекта TaskPermissionAspect");
            }
        } else {
            throw new IllegalArgumentException("Некорректные аргументы TaskPermissionAspect");
        }
    }
}
