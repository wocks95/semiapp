<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<jsp:include page="../layout/header.jsp">
   <jsp:param name="title" value="블로그 리스트"/>
</jsp:include>

<style>
  .wrap {
    width: 1060px;
    margin: 40 auto;
  }
  
  h1 {
    text-align: center;
    font-size: 30px;
    margin: 30px auto;
  }
  
  .wrap table {
    border: 0.5px solid black;
    border-collapse: collapse;
    width: 100%;
  }
  
  .wrap thead td {
    text-align: center;
    font-weight: 600;
  }
  
  .wrap td {
    border: 1px solid black;
    padding: 14px 0;
  }

  .number {
    text-align: right;
  }
  
  .wrap td: nth-of-type(1) {width: 60px; }
  .wrap td: nth-of-type(2) {width: 60px; }
  .wrap td: nth-of-type(3) {width: 200px; }
  .wrap td: nth-of-type(4) {width: 100px; }
  .wrap td: nth-of-type(5) {width: 400px; }
  .wrap td: nth-of-type(6) {width: 10px; }
  .wrap td: nth-of-type(7) {width: 10px; }
  
  .page {
    border:none;
  }
  
  .blogs:hover {
    cursur: pointer;
    background-color : #FCF9B4;
  }
  
  .search-wrap {
    text-align: center;
  }
  
  .ch input {
    pointer-events: auto;
    display: flex;
    text-align: center;
    vertical-align: middle;
  }
  
  .table-blog {
    font-size: 13px;
    width: 100%;
    border-top: 1px solid #ccc;
    border-bottom: 1px solid #ccc;
  }
  
  .blog {
    color: #333;
    display: inline-block;
    line-height: 1.4;
    word-break: break-all;
    vertical-align: middle;
  }
  
  .blog-ct {
    text-align: center;
  }

</style>

<h1>Blog List</h1>

<div>
  <button type="button" id="btn-write">새 블로그 작성하기</button>
</div>


<div>
  <form id="form-list" action="${contextPath}/blog/removes.do" method="post">
  <button type="submit">블로그 삭제</button>

<div class="wrap">
  <div style="text-align: right; cursor: pointer;">
    <a href="${contextPath}/blog/list.do?page=1&sort=DESC">최신순</a> | 
    <a href="${contextPath}/blog/list.do?page=1&sort=ASC">과거순</a>
</div>

<table id="table-blog">
  <caption class="number" style="text-align: right;" >총 ${total}개 블로그</caption>

  <thead class="list">
    <tr>
      <td>선택</td>
      <td>순번</td>
      <td>작성자</td>
      <td>제목</td>
      <td>내용</td>
      <td>조회수</td>
      <td>작성일시</td>
      <td>수정일시</td>
   </tr>
   </thead>
   
   <tbody>

      <c:forEach items="${blogs}" var="blog" varStatus="vs">
      <tr class="blogs" data-user-id="${blog.userDto.userId}" data-blog-id="${blog.blogId}">
      <!-- 
      1.checkbox 클릭 시 선택 삭제를 원하면, input 내 name(배열 이름)과
      value(checkbox 타입은 값 입력이 아닌 단순 클릭이기 때문에 별도 지정 필요)를 지정해줘야 함
      
      2.선택 삭제 버튼을 품고 있는 form 태그 안에 checkbox가 들어 있어야 함
      -->
        <td class="ch"><input type="checkbox" name="blogIds" value="${blog.blogId}"></td>
        <td class="blog-ct">${offset + vs.count}</td> <!-- count : index + 1 -->
        <td class="blog-ct">${blog.userDto.userEmail}</td>
        <td class="blog-ct">${blog.title}</td>
        <td class="blog-ct">${blog.contents}</td>
        <td class="blog-ct">${blog.hit}</td>
        <td class="blog-ct"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${blog.createDt}"/></td>
        <td class="blog-ct"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${blog.modifyDt}"/></td>
      </tr>
      </c:forEach>
   </tbody>
    
   <tfoot>
    <tr>
      <td colspan="8" class="page">${paging}</td>
      </tr>
   </tfoot>
</table>
</form>
</div>

<div class="search-wrap">
  <div>
    <form action="${contextPath}/blog/search.do">
      <input type="text" name="title" placeholder="제목 검색">
      <input type="text" name="userEmail" placeholder="사용자 이메일 검색">
      <input type="text" name="userName" placeholder="사용자 이름 검색">
      <input type="text" name="contents" placeholder="내용 검색"><br/>
      <input type="date" name="beginDt">-<input type="date" name="endDt">
      
      <button type="submit">검색</button>
    </form>
  </div>
</div>


<script>

  // 블로그 신규 작성
  function toBlogWrite() {
    document.getElementById('btn-write').addEventListener('click', (event) => {
      if('${sessionScope.loginUser.userId}' !== '') {
      location.href = '${contextPath}/blog/write.do';
      } else {
        alert('로그인 후 작성 가능합니다.');
      }
    })
  }
  
  // 블로그 상세 보기
  function toBlogDetail() {
    const blogs = document.getElementsByClassName('blogs');
    for(const blog of blogs) {
      blog.addEventListener('click', (event) => {
        
        // type이 체크 박스인 blogs 항목은 이벤트 제외
        if(event.target.type === 'checkbox') {
          return;
        }
        
        // 내가 쓴 블로그 클릭 시, 조회수 증가 없이 디테일 페이지로 이동 (수정, 삭제 가능한)
        // 다른 사람이 쓴 블로그 클릭 시 , 조회수 1 증가하고 디테일 페이지로 이동 (수정 삭제 불가)
        if('${sessionScope.loginUser.userId}' === event.currentTarget.dataset.userId) {
          location.href = '${contextPath}/blog/detail.do?blogId=' + event.currentTarget.dataset.blogId;
        } else {
          location.href = '${contextPath}/blog/increaseBlogHit.do?blogId=' + event.currentTarget.dataset.blogId;
        }
      })
    }
  }
 
  
  //블로그 선택 삭제
  function toBlogDelete() {
    const formList = document.getElementById('form-list');
    formList.addEventListener('submit', (event) => {
      if(!confirm('선택한 블로그를 삭제할까요?')) {
        event.preventDefault();  // 이벤트 취소
        return;                  // 이벤트 핸들러 실행 종료
      }
    })
  }
 

  toBlogWrite();
  toBlogDetail();
  toBlogDelete();
  
</script>

</div>

</body>
</html>