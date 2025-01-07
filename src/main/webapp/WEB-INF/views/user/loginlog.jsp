<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>

<jsp:include page="../layout/header.jsp">
  <jsp:param name="title" value="로그인 로그보기"/>
</jsp:include>
  
  <h1>로그인 로그보기</h1> 
  <div>전체 개수 ${count}개</div> 
  
  <table border="1">
    <thead>
      <tr>
        <td>번호</td>
        <td>이메일</td>
        <td>이름</td>
        <td>접속일시</td>
        <td>접속 IP</td>
        <td>접속 에이전트</td>
      </tr>
    </thead>
    <tbody>      
      <c:forEach items="${loginList}" var="l" varStatus="k">
        <tr class="logins" data-blog-id="${l.userId}" data-user-email="${l.userEmail}">
          <td>${offset + k.count}</td>
          <td>${l.userEmail}</td>    
          <td>${l.userName}</td>    
          <td><fmt:formatDate pattern="yyyy-MM-dd" value="${l.accDt}"/></td>
          <td>${l.accIp}</td>    
          <td>${l.userAgent}</td>              
        </tr>
      </c:forEach>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="6">${paging}</td>
      </tr>
    </tfoot>    
  </table>
      
  

</div>
  
</body>
</html>