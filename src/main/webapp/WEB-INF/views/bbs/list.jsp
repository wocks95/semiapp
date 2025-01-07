<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>

<jsp:include page="../layout/header.jsp">
  <jsp:param name="title" value="커뮤니티"/>
</jsp:include>

<style>
  .hidden {
    display: none;
  }
</style>
</head>
<body>

  <h1>게시글 작성하기(원글만 작성)</h1>
  <form action="${contextPath}/bbs/registBbs.do" method="post">    
    <!-- 로그인 안 한 사용자가 textarea 태그를 클릭하면 로그인 여부는 묻는 대화상자 띄우기 -->
    <textarea rows="5" cols="30" name="contents" placeholder="작성하려면 로그인 해 주세요." onclick="showAlert()" oninput="toggleBtn()" id="textarea"></textarea><br/>
    <input type="hidden" name="userId" value="${sessionScope.loginUser.userId}">
    <button type="submit" id="submitBtn" disabled>작성완료</button>
  </form>
  
  <hr/>
  
  <h1>게시글 목록</h1>
  <div>전체 게시글 개수 ${count}개</div>

  <div>${paging}</div>
  
  <div>
    <c:if test="${empty bbsList}">
      <div>검색 결과 없음</div>
    </c:if>
    <c:if test="${not empty bbsList}">
      <c:forEach items="${bbsList}" var="b" varStatus="k">
        <div>
          <span style="display: inline-block; width: 100px;">${offset + k.count}</span>
          <c:if test="${b.state == 1}">
            <span>삭제된 게시글입니다.</span>
          </c:if>
          <c:if test="${b.state == 0}">
            <!-- 댓글 수준 별 들여쓰기를 공백으로 구현합니다. -->
            <span style="display: inline-block;"><c:forEach begin="1" end="${b.depth}" step="1">&nbsp;&nbsp;</c:forEach></span>
            <!-- 댓글이나 대댓글은 내용 앞에 [Re]를 표시합니다. -->
            <c:if test="${b.depth > 0}">
              <span style="display: inline-block;">[Re]</span>
            </c:if>
            <pre style="display: inline-block; width: 500px;">${b.contents}</pre>
            <span style="display: inline-block;"> | ${b.userName} | </span>
            <span style="display: inline-block;">${b.createdDt}</span>          
            <button type="button" class="btn-form-reply">댓글달기</button>
            <button type="button" class="btn-delete" data-bbs-id="${b.bbsId}" data-user-id="${b.userId}">삭제</button>
          </c:if>
        </div>
        <div class="form-reply hidden">
          <form action="${contextPath}/bbs/registBbsReply.do" method="post">
            <!-- 원글의 depth, group_id, group_order를 포함해야 합니다. -->
            <input type="hidden" name="depth" value="${b.depth}">
            <input type="hidden" name="groupId" value="${b.groupId}">
            <input type="hidden" name="groupOrder" value="${b.groupOrder}">
            <input type="hidden" name="userId" value="${sessionScope.loginUser.userId}">
            <textarea rows="2" cols="100" name="contents" placeholder="작성하려면 로그인 해 주세요." onclick="showAlert()"></textarea>
            <button type="submit">작성완료</button>
          </form>
        </div>
      </c:forEach>
    </c:if>    
  </div>  
    
<!--        
  <div>
    <button type="button" onclick="searchBtn()">검색</button>
  </div>
-->    
  <div>
    <form action="${contextPath}/bbs/search.do">
      <div><input type="text" name="contents" placeholder="내용검색"></div>
      <div><input type="text" name="userName" placeholder="작성자검색"></div>
      <div><input type="date" name="beginDt"> - <input type="date" name="endDt"></div>
      <div><button type="submit" onclick="hiddenBtn()">검색</button></div>
    </form>
  </div>    
  
  <div>
    <button type="button" id="resetButton" onclick="reListBtn()">목록 초기화</button>
  </div>
  
  
  <script>
    function hiddenAllFormReply() {
      const formReply = document.getElementsByClassName('form-reply');
      for(const form of formReply) {
        form.classList.add('hidden');  // 모든 댓글 입력 폼에 class="hidden"을 추가합니다. 그러면 CSS에 의해서 화면에서 사라집니다.
      }
    }
    
    function displayFormReply() {
      const btnFormReply = document.getElementsByClassName('btn-form-reply');
      for(const btn of btnFormReply) {
        btn.addEventListener('click', (event) => {
          hiddenAllFormReply();  // 모든 댓글 입력 폼을 숨깁니다. 
          const target = event.currentTarget.parentElement.nextElementSibling;  // 화면에 표시할 댓글 입력 폼입니다.
          target.classList.remove('hidden');  // 화면에 표시할 댓글 입력 폼의 class="hidden" 속성을 없앱니다.
        })
      }
    }
    
    // 삭제 버튼
    function deleteBbs() {
      const btnDelete = document.getElementsByClassName('btn-delete');
      for(const btn of btnDelete) {
        btn.addEventListener('click', (event) => {
          if('${sessionScope.loginUser.userId}' === event.currentTarget.dataset.userId) {
            if(confirm('해당 게시글을 삭제할까요?')) {
              location.href = '${contextPath}/bbs/delete.do?bbsId=' + event.currentTarget.dataset.bbsId;
            }
          } else {
            alert('내가 작성한 글이 아닙니다.');
          }
        })
      }
    }

    // textarea 로그인 확인
    function getContextPath() {
      const url = location.href;                     /* http://localhost:8080/app09/main.do */
      const host = location.host;                    /* localhost:8080 */
      const begin = url.indexOf(host) + host.length; /* 7 + 14 = 21 : ContextPath의 시작 인덱스 */
      const end = url.indexOf('/', begin + 1);       /* 27          : Mapping의 시작 인덱스 */
      const contextPath = url.substring(begin, end); /* 인덱스 begin부터 인덱스 end 이전까지 */
      return contextPath;
    }
    
    function toLoginForm() {
      if(!location.href.includes('login.form')) {
        location.href = getContextPath() + '/user/login.form?url=' + location.href;
      }
    }
    
    function showAlert() {
      if('${sessionScope.loginUser.userId}' === "") {
        alert("글을 쓰려면 로그인을 해주세요.");
        toLoginForm();
      }
      
    }
    
    // 제출 버튼 활성화
    function toggleBtn() {
      const textarea = document.getElementById('textarea');
      const button = document.getElementById('submitBtn');
           
      if (textarea.value.trim() !== "") {
        button.disabled = false;
      } else {
        button.disabled = true;
      }
    }
    
    function searchBtn() {
      location.href = getContextPath() + '/bbs/search.form';
      
    }   
    
    function reListBtn() {
      location.href = getContextPath() + '/bbs/list.do';  
    }

    function hiddenBtn() {
      // const resetButton = document.getElementById('resetButton');
      // resetButton.hidden = false;
    }
    
    displayFormReply();
    deleteBbs();
  </script>  

</div>

</body>
</html>