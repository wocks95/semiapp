package com.min.semiapp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect // Aspect 선언입니다. 나는 AOP 방식으로 동작합니다. (종단 관심사)

public class BeforeAdvice {

  /**
   * PointCut 등록 메소드 작성 방법
   * 1. 반환타입 : void
   * 2. 메소드명 : 마음대로 작성
   * 3. 매개변수 : 없음
   * 4. 본문     : 없음
   * 5. 이름만 제공하는 메소드
   */
  // 이름이 Controller로 끝나는 모든 클래스의 모든 메소드를 포인트컷으로 등록합니다.
  @Pointcut("execution(* com.min.myapp.controller.*Controller.*(..))") 
  // *: 반환 타입이 어떤 타입이든지 상관없음을 의미
  // com.min.myapp.controller.*Controller: com.min.myapp.controller 패키지 내의 모든 Controller로 끝나는 클래스
  // *: Controller 클래스 내의 모든 메서드
  // (..): 메서드에 대한 모든 매개변수
  public void preparePointCut() {
    
  }
  
  
  
  /**
   * BeforeAdvice 작성 방법 (After Advice 작성 방법도 동일합니다.)
   * 1. 반환타입 : void
   * 2. 메소드명 : 마음대로 작성
   * 3. 매개변수 : JoinPoint joinPoint
   * @param joinPoint
   */
  @Before(value ="preparePointCut()")
  public void beforeAdvice(JoinPoint joinPoint) {
    
    // Advice 작성 : 동작할 코드를 작성하는 곳입니다. 지정된 포인트컷(컨트롤러가 작동하기 전)에 작성하라.
    System.out.println("Before Advice가 동작합니다.");
  }
  
}
