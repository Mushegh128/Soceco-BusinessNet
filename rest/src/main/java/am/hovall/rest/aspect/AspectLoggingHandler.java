package am.hovall.rest.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class AspectLoggingHandler {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController * )")
    public void controller() {
    }

    @Pointcut("execution(* am.hovall.rest.endpoint.*.*(..))")
    public void allMethodsInEndpoint() {
    }


    @Before("controller()&& allMethodsInEndpoint())")
    public void beforeMethodWorks(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Entering in Method :  " + joinPoint.getSignature().getName());
        log.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
        log.info("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
        log.info("Target class : " + joinPoint.getTarget().getClass().getName());
        if (request.getSession() != null) {
            log.info("Start Header Section of request ");
            log.info("Method Type : " + request.getMethod());
            log.info("Request Path info :" + request.getServletPath());
            log.info("End Header Section of request ");
        }
    }

    @Around("controller()&&allMethodsInEndpoint()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("Method " + className + "." + methodName + " ()" + " execution time : "
                    + elapsedTime + " ms");

            return result;
        } catch (IllegalArgumentException e) {
            log.info("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }

    @AfterThrowing(pointcut = "controller()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.info("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        log.info("Cause : " + exception.getMessage());
    }
}
