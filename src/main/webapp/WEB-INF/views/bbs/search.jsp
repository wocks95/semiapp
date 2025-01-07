<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>

<jsp:include page="../layout/header.jsp">
  <jsp:param name="title" value="커뮤니티 검색"/>
</jsp:include>

  <h1>Search</h1>

  <div>
    <form action="${contextPath}/bbs/search.do">
      <div><input type="text" name="contents" placeholder="내용검색"></div>
      <div><input type="text" name="userName" placeholder="작성자검색"></div>
      <div><input type="date" name="beginDt"> - <input type="date" name="endDt"></div>
      <div><button type="submit">검색</button></div>
    </form>
  </div>

  <div>
    <c:if test="${empty searchList}">
      <div>검색 결과 없음</div>
    </c:if>
    <c:if test="${not empty searchList}">    
      <div>검색 결과 ${searchCount}개</div>
      <c:forEach items="${searchList}" var="b" varStatus="vs">
        <div class="notices" data-notice-id="${b.bbsId}">
          ${offset + vs.count} | ${b.contents} | ${b.userName} | <fmt:formatDate value="${b.createdDt}" pattern="yyyy.MM.dd HH:mm:ss"/>
        </div>
      </c:forEach>
      <div>${paging}</div>
    </c:if>
  </div>

</body>
</html>