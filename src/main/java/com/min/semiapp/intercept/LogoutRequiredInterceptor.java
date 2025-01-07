package com.min.semiapp.intercept;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LogoutRequiredInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws Exception {
    HttpSession session = request.getSession();
    if(session.getAttribute("loginUser") !=null) {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("location.href = '" + request.getContextPath() + "'");
      out.println("</script>");
      return false;
    }
    return true;

  }
}
