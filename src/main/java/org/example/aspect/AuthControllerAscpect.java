package org.example.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component


public class AuthControllerAscpect {
    @Autowired
    private HttpServletRequest request;

    @Before("within(org.example.controller..*)")
    public void logRequest(JoinPoint joinPoint) {
        String requestedUrl = request.getRequestURL().toString();
        String ipAddress = request.getRemoteAddr();
        String username = (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "Anonymous";

        System.out.println("Requested URL: " + requestedUrl);
        System.out.println("IP Address: " + ipAddress);
        System.out.println("Username: " + username);
        System.out.println("Method Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "within(org.example.controller..*)", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {
        System.out.println("Response: " + result);
    }
}
