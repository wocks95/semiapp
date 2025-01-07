package com.min.semiapp.intercept;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;


public class LoginRequiredInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    // 세션에 loginUser 값이 없으면 로그인 화면으로 이동하는 기능
    
    HttpSession session = request.getSession();
    if(session.getAttribute("loginUser") == null) {
      response.setContentType("text/html; charset=UTF-8");
      
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("if(confirm('로그인이 필요한 기능입니다. 로그인 할까요?')) {");
      out.println("   location.href = '" + request.getContextPath() + "/user/login.form?url=" + request.getRequestURL() + "'");
      out.println("} else {");
      out.println(" history.back()");
      out.println("}");
      out.println("</script>");
      out.close();
      
      return false; // 기존 요청을 처리하지 않습니다.
    } // if
    
    return true; // 기존 요청을 그대로 처리합니다.
    
  } //preHandle()
  
}
