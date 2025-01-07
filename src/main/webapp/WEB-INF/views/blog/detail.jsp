<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<jsp:include page="../layout/header.jsp">
   <jsp:param name="title" value="${blog.title}"/>
</jsp:include>

<style>
  
  h1 {
    font-size: 28px;
    color: #333333;
    font-weight: 400;
    text-align: center;
    margin-bottom: 60px;
  }

  #contents {
    width: 100%;
    height: 300px;
  }
  
  .btn-detail {
    display: flex;
    gap: 10px; 
  }
  
  .hidden {
    display: none;
  }
  
</style>

  <h1>Blog Detail</h1>
  
  <form id = "form-detail" method="post">
    <input type="hidden" name="blogId" value="${blog.blogId}">
    
    <div>작성자 ${blog.userDto.userEmail}</div>
    <div>작성일시 <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${blog.createDt}"/></div>
    <div>수정일시 <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"  value="${blog.modifyDt}"/></div>
    <div>조회수 ${blog.hit}</div>
  
    <div>
      <label for="title">제목</label>
      <input type="text" name="title" id="title" value="${blog.title}">
    </div>
  
    <div>
      <textarea name="contents" id="contents" placeholder="내용">${blog.contents}</textarea>
    </div>
    
    <div class="btn-detail">
      <button type="reset" id="btn-reset">수정초기화</button>
      <button type="button" id="btn-modify" >수정 완료</button>
      <button type="button" id="btn-remove">블로그 삭제</button>
      <button type="button" id="btn-list">블로그 리스트</button>
    </div>
  </form>
  
  <br/>
  
  <!-- 블로그 댓글 달기 -->
  
    <div class="comment">
    <h3>블로그 댓글 달기</h3>
    <form action="${contextPath}/blog/registComment.do" method="post">
      <input type="hidden" name="userDto.userId" value="${sessionScope.loginUser.userId}" >
      <input type="hidden" name="blogId" value="${blog.blogId}" >
      <textarea rows="5" cols="30" name="contents" placeholder="댓글 작성을 위해 로그인 해주세요."></textarea><br/>
      <button type="submit">작성완료</button>
    </form>
    </div>

  <br/>
  <hr/>
<div>

    <h3>댓글 리스트</h3>
  
    <!-- 블로그 대댓글 달기 -->
    <c:forEach items="${commentList}" var="c" varStatus="k">
      <div>        
        <span style="display: inline-block; width: 100px;">${offset + k.count}</span>
        <c:if test="${c.state == 1}">
          <span>삭제된 댓글입니다.</span>
        </c:if>
        <c:if test="${c.state == 0}">
          <!-- 댓글 수준 별 들여쓰기를 공백으로 구현합니다. -->
          <span style="display: inline-block;"><c:forEach begin="1" end="${c.depth}" step="1">&nbsp;&nbsp;</c:forEach></span>
          <!-- 댓글이나 대댓글은 내용 앞에 [Re]를 표시합니다. -->
          <c:if test="${c.depth > 0}">
            <span style="display: inline-block;">[Re]</span>
          </c:if>
          <pre style="display: inline-block; width: 500px;">${c.contents}</pre>
          <!-- 댓글 작성한 사람의 이메일 노출 -->
          <span style="display: inline-block;">${c.userDto.userEmail}</span>
          <span style="display: inline-block;">${c.createDt}</span>
          <span style="display: inline-block;">${c.modifyDt}</span>
          <button type="button" class="btn-form-reply">추가</button>
          <button type="button" class="btn-delete" data-comment-id="${c.commentId}">삭제</button>
        </c:if>
      </div>
      <div class="form-reply hidden">
        <form action="${contextPath}/blog/registCommentReply.do" method="post">
          <!-- 원글의 depth, group_id, group_order를 포함해야 합니다. -->
          <input type="hidden" name="depth" value="${c.depth}">
          <input type="hidden" name="groupId" value="${c.groupId}">
          <input type="hidden" name="groupOrder" value="${c.groupOrder}">
          <input type="hidden" name="userDto.userId" value="${sessionScope.loginUser.userId}" >
          <input type="hidden" name="blogId" value="${blog.blogId}" >
          <textarea rows="5" cols="30" name="contents" placeholder="대댓글 내용을 작성해주세요."></textarea><br/>
          <button type="submit">작성완료</button>
        </form>
      </div>
    </c:forEach>
  </div>
  
  <script>
      const formDetail = document.getElementById('form-detail');
    
    // 블로그 수정
    function submitForm() {
      const title = document.getElementById('title');
      document.getElementById('btn-modify').addEventListener('click', (event) => {
        if(title.value === '') {
          alert('제목 필수 입력!');
          title.focus();
          return;
        }
        formDetail.action = '${contextPath}/blog/modify.do';
        formDetail.submit();
      })
    }
    
    // 블로그 삭제
    function deleteBlog() {      
      document.getElementById('btn-remove').addEventListener('click', (event) => {
        if(confirm('현재 블로그를 삭제할까요?')) {
          formDetail.action = '${contextPath}/blog/remove.do';
          formDetail.submit();
        }
      })
    }
    
    // 블로그 리스트로 돌아가기
    function toBlogList() {
      document.getElementById('btn-list').addEventListener('click', (event) => {
        location.href= '${contextPath}/blog/list.do';
      }) 
    }
    
    // 블로그 작성자에 따른 디테일 버튼 유무 (블로그 작성자만 수정 및 삭제 가능하도록)
    function BlogDetail() {
      const loggedInUserId = '${sessionScope.loginUser.userEmail}';
      
      const btnreset = document.getElementById('btn-reset');
      const btnmodify = document.getElementById('btn-modify');
      const btnremove = document.getElementById('btn-remove');
        
      if(loggedInUserId === '${blog.userDto.userEmail}') {
        btnreset.style.display = 'block';
        btnmodify.style.display = 'block';
        btnremove.style.display = 'block';
        
      } else {
        btnreset.style.display = 'none';
        btnmodify.style.display = 'none';
        btnremove.style.display = 'none';
      }
    }

    // 댓글 입력창 숨기기
    function hiddenAllFormComment() {
      const formComment = document.getElementsByClassName('form-reply');
      for(const form of formComment) {
        form.classList.add('hidden');  // 모든 댓글 입력 폼에 class="hidden"을 추가합니다. 그러면 CSS에 의해서 화면에서 사라집니다.
      }
    }
    
    // 댓글 입력창 노출하기
    function displayFormComment() {
      const btnFormComment = document.getElementsByClassName('btn-form-reply');
      for(const btn of btnFormComment) {
        btn.addEventListener('click', (event) => {
          hiddenAllFormComment();  // 모든 댓글 입력 폼을 숨깁니다. 
          const target = event.currentTarget.parentElement.nextElementSibling;  // 화면에 표시할 댓글 입력 폼입니다.
          target.classList.remove('hidden');  // 화면에 표시할 댓글 입력 폼의 class="hidden" 속성을 없앱니다.
        })
      }
    }
    
    // 댓글 삭제
    function deleteComment() {
      const btnDelete = document.getElementsByClassName('btn-delete');
      for(const btn of btnDelete) {
        btn.addEventListener('click', (event) => {
          if(confirm('해당 댓글을 삭제할까요?')) {
            location.href='${contextPath}/blog/deleteCommentReply.do?commentId=' + event.currentTarget.dataset.commentId;
          }
        })
      }
    }
    
    // 내가 쓴 블로그에는 댓글 작성 폼 숨기기
    function hiddenComment() {
      const loggedInUserId = '${sessionScope.loginUser.userId}';
      const blogUserId = '${blog.userDto.userId}';
      
      // 모든 댓글 입력 폼을 가져옵니다.
      const formComments = document.getElementsByClassName('comment');

      // 로그인한 사용자와 블로그 작성자가 동일하면 댓글 폼을 숨깁니다.
      if (loggedInUserId === blogUserId) {
        for (const form of formComments) {
          form.style.display = 'none';  // 각 댓글 폼의 display 속성을 'none'으로 설정
        }
      }
    }

    submitForm();
    deleteBlog();
    toBlogList();
    BlogDetail();
    hiddenAllFormComment();
    displayFormComment();
    deleteComment();
    hiddenComment();
    
  </script>

</body>
</html>