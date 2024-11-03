package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.entity.model.InvestmentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InvestorServiceAspect {

    private static final Logger logger = LoggerFactory.getLogger(InvestorServiceAspect.class);

    // Logging before the execution of the create method
    @Before("execution(* org.example.entity.service.InvestmentService.create(..)) && args(investment, cardId)")
    public void beforeCreate(JoinPoint joinPoint, InvestmentModel investment, Long cardId) {
        logger.info("Attempting to create a new investment for investor ID: {} and card ID: {}",
                investment.getInvestor().getId(), cardId);
    }

    // Executing logic if the create method completes successfully
    @AfterReturning(pointcut = "execution(* org.example.entity.service.InvestmentService.create(..))", returning = "result")
    public void afterReturningCreate(JoinPoint joinPoint, Object result) {
        InvestmentModel createdInvestment = (InvestmentModel) result;
        logger.info("Investment with ID {} created successfully.", createdInvestment.getId());
    }

    // Handling exceptions thrown by the create method
    @AfterThrowing(pointcut = "execution(* org.example.entity.service.InvestmentService.create(..))", throwing = "exception")
    public void afterThrowingCreate(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception occurred while creating investment: {}", exception.getMessage(), exception);
    }
}
