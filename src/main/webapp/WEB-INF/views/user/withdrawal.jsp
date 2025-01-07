<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>

<jsp:include page="../layout/header.jsp">
  <jsp:param name="title" value="탈퇴 화원보기"/>
</jsp:include>
  
  <h1>탈퇴 화원보기</h1>
  <div>전체 개수 ${count}개</div>

  <table border="1">
    <thead>
      <tr>
        <td>번호</td>
        <td>이메일</td>
        <td>이름</td>
        <td>접속일시</td>
      </tr>
    </thead>
    <tbody>      
      <c:forEach items="${withdrawalList}" var="w" varStatus="k">
        <tr class="withdrawals">
          <td>${offset + k.count}</td>
          <td>${w.userEmail}</td>    
          <td>${w.userName}</td>    
          <td><fmt:formatDate pattern="yyyy-MM-dd" value="${w.delDt}"/></td>            
        </tr>
      </c:forEach>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="4">${paging}</td>
      </tr>
    </tfoot>    
  </table>


</div>
  
</body>
</html>