<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<jsp:include page="../layout/header.jsp">
  <jsp:param name="title" value="공지사항 수정"/>
</jsp:include>

  <h1>Notice Correction</h1>
  
  <div>
    <form  id="form-edit" action="${contextPath}/notice/modify.do" method="post" enctype="multipart/form-data">
      <input type="hidden" name="noticeId" value="${notice.noticeId}">
      <input type="text" name="title" id="title" value="${notice.title}" placeholder="제목"><br/>
      <textarea rows="5" cols="30" name="contents" id="contents" placeholder="내용">${notice.contents}</textarea><br/>
      <button type="reset">수정 초기화</button>
      <button type="submit" id="btn-modify">수정 완료</button>
      <button type="button" id="btn-list">블로그 목록</button><br/>
      <input type="file" name="files" id="files" multiple>
    </form>
  </div>

  <div>
    <h3>기존 첨부파일</h3>
    <form id="form-attach" action="${contextPath}/notice/removeAttaches.do" method="post" enctype="multipart/form-data">
      <input type="hidden" name="noticeId" value="${notice.noticeId}">
      <button type="submit">선택삭제</button>
      <c:if test="${not empty attachList}">
        <ul>
          <c:forEach items="${attachList}" var="attach">
            <li>
              <input type="checkbox" name="attachIds" class="attachIds" value="${attach.attachId}">
              ${attach.originalFilename}
            </li>
          </c:forEach>
        </ul>
      </c:if>
      <c:if test="${empty attachList}">
          <p>첨부파일이 없습니다.</p>
      </c:if>   
    </form>
  </div>
  
  <script>
    function logForm() {
      const formEdit = document.getElementById('form-edit');
      document.getElementById('btn-modify').addEventListener('click', (event) => {
        if('${sessionScope.loginUser}' === '') {
          alert('로그인이 필요합니다.');
          location.href = '${contextPath}/user/login.form';
          event.preventDefault();
          return;
        }
      })
    }   
    
    function submitForm() {
      const formWrite = document.getElementById('form-edit');
      const title     = document.getElementById('title');
      const contents  = document.getElementById('contents');
      formWrite.addEventListener('submit', (event) => {
        if(title.value === '') {
          alert('제목은 필수입니다.');
          title.focus();
          event.preventDefault();
          return;
        }
        if(contents.value === '') {
          alert('내용을 작성해주시길 바랍니다.');
          contents.focus();
          event.preventDefault();
          return;
        }
      })
    }
    
    
    
    function toNoticeList() {
      document.getElementById('btn-list').addEventListener('click', (event) => {
        location.href = '${contextPath}/notice/list.do';
      })
    } 
    function attachCheck() {
      const files = document.getElementById('files');
      
      // 개별 파일 크기
      const limitPerSize = 1024 * 1024 * 10;
      
      // 전체 파일 크기
      const limitTotalSize = 1024 * 1024 * 100;
      
      // 전체 파일 크기를 저장할 변수
      let totalSize = 0;
      
      files.addEventListener('change', (event) => {
        for(const file of event.currentTarget.files) {
          if(file.size > limitPerSize) {
            alert('각 첨부 파일의 크기는 최대 10MB입니다.');
            event.currentTarget.value = '';
            return; // 이벤트 핸들러 취소
          }
          
          totalSize += file.size;
          
          if(totalSize > limitTotalSize) {
            alert('전체 첨부 파일의 크기는 최대 100MB입니다.');
            event.currentTarget.value = '';
            return;
          }
        }
      })
    }

    logForm();
    submitForm();
    toNoticeList();
    attachCheck();
  
  </script>

</body>
</html>