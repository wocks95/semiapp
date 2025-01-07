<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri ="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<jsp:include page="../layout/header.jsp">
  <jsp:param name="title" value="${n.title}"/>
</jsp:include>


  <h1>Notice Detail</h1>
  
  
<form id="form_detail" method="post">
  <c:if test= "${empty n.userDto.userEmail}">
    <div>작성자 정보 없음</div>
  </c:if>
  <c:if test="${not empty n.userDto.userEmail}">
    <div>작성자 ${n.userDto.userName}(${n.userDto.userEmail})</div>
  </c:if>

  <div>작성일시<fmt:formatDate value="${n.createDt}" pattern="yyyy-MM-dd a hh:mm:ss"/></div>
  <div>수정일시<fmt:formatDate value="${n.modifyDt}" pattern="yyyy-MM-dd a hh:mm:ss"/></div>

  <div>
	  <h1>제목 : ${n.title}</h1>
	  <pre>내용 : ${n.contents}</pre>
  </div>
  
  <div style="background-color: beige">
    <h4>첨부 파일</h4>
    <c:forEach items="${attachList}" var="a">
      <div>
        <a href="${contextPath}/notice/download.do?attachId=${a.attachId}" class="download-link">${a.originalFilename}</a>
        <span>Download Count(${a.downloadCount})</span>
      </div>
    </c:forEach>
  </div>
  
  
  <button type="button" id="back-list" >목록</button>
  <button type="button" id="remove-detail">삭제하기</button>
  <button type="button" id="btn-edit">수정하기</button>
</form>


  <script>
    
    function tolimitsButtons() {
      const loggedInUserEmail = '${sessionScope.loginUser.userEmail}';
      const noticeUserEmail = '${n.userDto.userEmail}';
    
      const editButton = document.getElementById('btn-edit');
      const deleteButton = document.getElementById('remove-detail');
      
      if(loggedInUserEmail !== noticeUserEmail) {
        editButton.style.display = 'none';
        deleteButton.style.display = 'none';        
      } 
    }
    
  
    function confirmDownload() {
      const downloadLink = document.getElementsByClassName('download-link');
      for(const link of downloadLink) {
        link.addEventListener('click', (event) => {
          if(!confirm('해당 첨부 파일을 다운로드 할까요?')) {
            event.preventDefault(); // <a> 태그의 href 이동을 막는 코드
            return;
          }
        })
      }   
    }
    function backList() {
        const backButton = document.getElementById('back-list');
        backButton.addEventListener('click', (event) => {
        location.href = '${contextPath}/notice/list.do';
      })
    }
    function removeDetail() {
      const removeButton = document.getElementById('remove-detail');
      removeButton.addEventListener('click', (event) => {
        if('${sessionScope.loginUser}' == '') {
          alert('로그인이 필요합니다.');
          location.href = '${contextPath}/user/login.form';
          event.preventDefault();
          return;
        }
        if(confirm('현재 게시글을 삭제할까요?')) {
          location.href = '${contextPath}/notice/remove.do?noticeId=' + ${n.noticeId};
        }
      })
    }
    
    function correctionBtn() {
      const  btnEdit = document.getElementById('btn-edit');
      btnEdit.addEventListener('click', (event) => {
        if('${sessionScope.loginUser}'=== '') {
          alert('로그인이 필요합니다.');
          location.href = '${contextPath}/user/login.form';
          event.preventDefault();
          return;
        }
          location.href = '${contextPath}/notice/edit.do?noticeId=' + ${n.noticeId};
      })
    }
    tolimitsButtons();
    confirmDownload();
    backList();
    removeDetail();
    correctionBtn();
  </script>
  
</div>
</body>
</html>