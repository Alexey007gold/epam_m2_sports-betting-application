package com.epam.training.sportsbetting.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Logs the following information of the application’s service methods
 * Parameter(s) passed to the called method
 * Return value of the called method
 * Execution time of the called method
 */
@Aspect
@Component
@Slf4j
public class ServiceMethodsLogger {

    @Around("execution(* com.epam.training.sportsbetting.service.impl..*(..))")
    public Object logServiceMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        
        StringBuilder builder = new StringBuilder();

        builder.append("----------Service method call-----------\n");
        long start = System.currentTimeMillis();
        appendMethodCallInfo(joinPoint, start, builder);

        Object returnVal = joinPoint.proceed();
        long time = System.currentTimeMillis() - start;

        appendMethodResult(builder, returnVal, time);
        builder.append("**********Service method call end**********");

        log.debug(builder.toString());
        return returnVal;
    }

    private void appendMethodCallInfo(ProceedingJoinPoint joinPoint, long timestamp, StringBuilder builder) {
        Object[] args = joinPoint.getArgs();

        builder.append("Class: ")
            .append(joinPoint.getSignature().getDeclaringType().getName())
            .append("\n");
        builder.append("Method: ")
            .append(joinPoint.getSignature().getName())
            .append("\n");
        builder.append("\tArgs:\n");
        for (Object arg : args) {
            builder.append("\t\tType: ").append(arg.getClass())
                .append("\t\tValue: ")
                .append(arg.toString())
                .append("\n");
        }

        builder.append("Call timestamp: ").append(timestamp);
    }

    private void appendMethodResult(StringBuilder builder, Object returnVal, long time) {
        if (returnVal != null) {
            builder.append("\tReturn value:\n");
            builder.append("\t\tType: ")
                .append(returnVal.getClass())
                .append("\t\tValue: ")
                .append(returnVal.toString())
                .append("\n");
        } else {
            builder.append("\tNo return value\n");
        }

        builder.append(String.format("Time for method execution: %dms", time)).append("\n");
    }
}
