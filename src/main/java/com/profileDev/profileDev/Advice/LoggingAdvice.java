package com.profileDev.profileDev.Advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAdvice {

    Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);
    @Autowired
    HttpServletRequest httpServletRequest;

    @Before(value = "execution(* com.profileDev.profileDev.controller.ProfileDevController.getProfiles(..))")
    public void beforeAdvice(){
        logger.info("LoggingAdvice > beforeAdvice()");
    }

    @After(value = "execution(* com.profileDev.profileDev.controller.ProfileDevController.getProfiles(..))")
    public void afterAdvice(){
        logger.info("LoggingAdvice > afterAdvice()");
    }

    @AfterReturning(value = "execution(* com.profileDev.profileDev.controller.ProfileDevController.getProfiles(..))")
    public void afterReturningAdvice(){
        logger.info("LoggingAdvice > afterReturningAdvice()");
    }

    @Around(value = "execution(* com.profileDev.profileDev.controller.ProfileDevController.*(..))")       // using this returning value is not visible
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("LoggingAdvice > aroundAdvice() > before .proceed()");
        Object object = proceedingJoinPoint.proceed();
        logger.info("LoggingAdvice > aroundAdvice() > after .proceed() > URL : "+httpServletRequest.getRequestURL()+" is hit from ip : "+httpServletRequest.getRemoteHost()+" by user : "+httpServletRequest.getRemoteUser());
        return object;
    }


/*    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {   // this fun() to get details of triggered method
        ObjectMapper mapper = new ObjectMapper();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] array = pjp.getArgs();
        logger.info("method invoked " + className + " : " + methodName + "()" + "arguments : " + mapper.writeValueAsString(array));
        Object object = pjp.proceed();
        logger.info(className + " : " + methodName + "()" + "Response : " + mapper.writeValueAsString(object));
        return object;
    }
*/
}
