package com.min.semiapp.config;

// import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.min.semiapp.aop.AroundAdvice;
import com.min.semiapp.aop.BeforeAdvice;

/**
 * AOP 동작 확인을 위해서는 <code>@EnableAspectJAutoProxy</code> 와
 * <code>@Bean</code> Annotation의 주석을 해제해야 합니다.
 * @author Administrator
 *
 */


@Configuration //Spring Container에 bean을 만들어두는 클래스입니다. root-context.xml의 자바 버전이다.
// @EnableAspectJAutoProxy //AOP 동작을 위해서 필요합니다.
public class AppConfig {

  // @Bean // bean을 만드는 메소드입니다. bean 의 타입은 BeforeAdvice.class이고 bean의 이름은 before입니다.
  public BeforeAdvice before() {
    return new BeforeAdvice();
  }
  
  // @Bean
  public AroundAdvice around() {
    return new AroundAdvice();
  }
}
