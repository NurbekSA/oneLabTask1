//package org.example.aspect;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.example.Entity.model.InvestmentModel;
//import org.example.Entity.model.InvestorModel;
//import org.example.Entity.repository.InvestmentRepo;
//
//import java.util.List;
//
//@Aspect
//@Component
//public class InvestorServiceAspect {
//
//    @Autowired
//    private InvestmentRepo investmentRepo;
//
//    // Определение общего среза для метода findByIin
//    @Pointcut("execution(* org.example.repository.InvestorRepo.findByIin(..))")
//    public void findByIinPointcut() {}
//
//    // Before Advice
//    @Before("findByIinPointcut() && args(iin)")
//    public void beforeFindByIin(String iin) {
//        System.out.println("Идет поиск инвестора с ИИН: " + iin);
//    }
//
//    // AfterReturning Advice
//    @AfterReturning(pointcut = "findByIinPointcut()", returning = "investor")
//    public void afterReturningFindByIin(InvestorModel investor) {
//        if (investor != null) {
//            List<InvestmentModel> investments = investmentRepo.findByInvestor(investor);
//            if (!investments.isEmpty()) {
//                System.out.println("Инвестиции инвестора:");
//                investments.forEach(investment -> System.out.println(investment.toString()));
//            } else {
//                System.out.println("У инвестора нет инвестиций.");
//            }
//        } else {
//            System.out.println("Инвестор не найден.");
//        }
//    }
//
//    // AfterThrowing Advice: Обработка исключений
//    @AfterThrowing(pointcut = "findByIinPointcut()", throwing = "ex")
//    public void afterThrowingFindByIin(Exception ex) {
//        System.out.println("Ошибка при выполнении поиска инвестора: " + ex.getMessage());
//    }
//
//    // Around Advice: Время выполнения и дополнительная логика
//    @Around("findByIinPointcut()")
//    public Object aroundFindByIin(ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        System.out.println("Начало выполнения метода findByIin...");
//
//        // Выполнение целевого метода
//        Object result = null;
//        try {
//            result = joinPoint.proceed();
//        } catch (Exception ex) {
//            System.out.println("Исключение в методе findByIin: " + ex.getMessage());
//            throw ex;
//        }
//
//        long endTime = System.currentTimeMillis();
//        System.out.println("Время выполнения метода findByIin: " + (endTime - startTime) + " мс");
//
//        return result;
//    }
//}
