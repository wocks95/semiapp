<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>

<jsp:include page="./layout/header.jsp">
  <jsp:param name="title" value="Welcome"/>
</jsp:include>
  <style>
    #winter {
      height: 600px;
      margin-left: 330px;
      margin-top: 10px;
    }  
  </style>
  
  
  <img id="winter" src = "${contextPath}/assets/images/winter1.jpg">

</div>
  
</body>
</html>