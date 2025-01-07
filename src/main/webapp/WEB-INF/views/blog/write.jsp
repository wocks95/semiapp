<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>

<jsp:include page="../layout/header.jsp">
   <jsp:param name="title" value="블로그 작성"/>
</jsp:include>

<style>
  #contents {
    width: 70%;
    min-height: 100px;
    margin: 30px auto;
  }
  
   h1 {
    font-size: 28px;
    color: #333333;
    font-weight: 400;
    text-align: center;
    margin-bottom: 60px;
  }
  
  write_email, title {
   text-align: center;
   margin: 20px;
  }
  
</style>


  <h1>Blog Write</h1>
  
  <form id="form-write" action="${contextPath}/blog/regist.do" method="post">
   
    <div class="write_email">
      작성자 이메일 : ${sessionScope.loginUser.userEmail}
    </div>
    
    <input type="hidden" name="userDto.userId" value="${sessionScope.loginUser.userId}">
    
    <span class="title">
      <label for="title">제목</label>
      <input type="text" name="title" id="title">
    </span>
    
    <div>
      <textarea name="contents" id="contents" placeholder="블로그 내용을 작성해주세요."></textarea>
    </div>
    
    <div>
      <button type="submit">작성 완료</button>
      <button type="reset">입력 초기화</button>
    </div>
 
  </form>
  
  <script>
    // 블로그 작성 시 제목 필수 입력 알럿 노출
    function submitForm() {
      const formWrite = document.getElementById('form-write');
      const userId = document.getElementById('user_id');
      const title = document.getElementById('title');
      formWrite.addEventListener('submit', (event) => {
        if(title.value == '') {
          alert('제목 필수 입력!');
          title.focus();
          event.preventDefault();
          return;
        }
      })
    }
    
    submitForm();
  </script>

</body>
</html>