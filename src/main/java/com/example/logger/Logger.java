package com.example.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prokade
 */
@Aspect
@Component
public class Logger {


    private ObjectMapper objectMapper = new ObjectMapper();

    //@Around("execution(* *(..)) && @annotation(com.example.annotation.LogMethodParam)")
    @Around("@annotation(com.example.annotation.LogMethodParam)")
    private Object logMethodInvocationAndParameters(ProceedingJoinPoint jp) throws Throwable {
        String[] args = ((MethodSignature) jp.getSignature()).getParameterNames();
        Object[] values = jp.getArgs();
        Map<String, Object> params = new HashMap<>();
        Object result = jp.proceed(jp.getArgs());
        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                params.put(args[i], values[i]);
            }
        }

        String methodName = jp.getSignature().getName();
        org.slf4j.Logger logger = LoggerFactory.getLogger(methodName);
        if (!params.isEmpty()) logger.info("<-> " + methodName + " params \n" +
                objectMapper.writeValueAsString(params) + "\n");

        return result;
    }

}
