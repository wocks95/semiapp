package com.min.semiapp.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AroundAdvice {

  @Pointcut("execution(* com.min.myapp.service.*Impl.*(..))")
  public void preparePointCut() {
    
  }
  
  /**
   * Around Advice 메소드 작성 방법
   * 1. 반환타입 : Object
   * 2. 메소드명 : 마음대로 작성
   * 3. 매개변수 : ProceedingJoinPoint proceedingJoinPoint
   * @return ProceedingJoinPoint의 proceed() 메소드 호출 결과를 반환
   */
  @Around(value= "preparePointCut()")
  public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    
    System.out.println("서비스 메소드 동작 이전 시점입니다.");
    
    Object obj = proceedingJoinPoint.proceed(); // 서비스 메소드가 실행하는 시점을 의미합니다.
   
    System.out.println("서비스 메소드 동작 이후 시점입니다.");
    
    return obj;
  }
  
  
}
