package com.min.semiapp.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.min.semiapp.dao.IBlogDao;
import com.min.semiapp.dto.BlogDto;
import com.min.semiapp.service.IBlogService;
import com.min.semiapp.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements IBlogService {

  private final IBlogDao blogDao;
  private final PageUtil pageUtil;
  
  @Override
  public Map<String, Object> getBlogList(HttpServletRequest request) {
    
    // 페이징 처리를 위한 각 요소 초기값 주기
    // page : 1
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
 
    // display : 10
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("10"));
    
    // total
    int total = blogDao.selectBlogCount();
    
    // 페이징을 위한 요소 정보 처리
    pageUtil.setPaging(page, display, total);
    int offset = pageUtil.getOffset();
    
    // sort 파라미터 (디폴트 DESC)
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    // 목록 가져오기 (Map으로 페이지 처리를 위한 목록 부르기)
    List<BlogDto> blogs = blogDao.selectBlogList(Map.of("offset", offset, "display", display, "sort", sort));
    
    // 페이지 이동 링크 가져오기
    String paging = pageUtil.getPaging(request.getContextPath() + "/blog/list.do", sort);
    
    // 결과 반환 (블로그 목록, 전체 블로그 수, 페이징 링크, 순번)
    return Map.of("blogs", blogs, "total", total, "paging", paging, "offset", offset);
  }
  
  @Override
  public String registBlog(BlogDto blogDto) {
    return blogDao.insertBlog(blogDto) == 1 ? "블로그 작성 성공" : "블로그 작성 실패";
  }
 
  @Override
  public BlogDto getBlogById(int blogId) {
    return blogDao.selectBlogById(blogId);
  }
  
@Override
public String modifyBlog(BlogDto blogDto) {
  return blogDao.updateBlog(blogDto) == 1 ? "블로그 수정 완료" : "블로그 수정 실패";
}

@Override
public String removeBlog(int blogId) {
  return blogDao.deleteBlog(blogId) == 1 ? "블로그 삭제 완료" : "블로그 삭제 실패";
}

@Override
public Map<String, Object> getSearchList(HttpServletRequest request) {
  
  String title = request.getParameter("title");
  String userEmail = request.getParameter("userEmail");
  String userName = request.getParameter("userName");
  String contents = request.getParameter("contents");
  String beginDt = request.getParameter("beginDt");
  String endDt = request.getParameter("endDt");
  
  // 요청 파라미터 Map 만들기
  Map<String, Object> param = Map.of("title", title,
                                     "userEmail", userEmail,
                                     "userName", userName,
                                     "contents", contents,
                                     "beginDt", beginDt,
                                     "endDt", endDt);
  
  // 검색 결과 목록 가져오기
  List<BlogDto> searchList = blogDao.selectBlogSearchList(param);
  
  // 검색 결과 갯수 가져오기
  int searchCount = blogDao.selectBlogSearchCount(param);
  
  // 페이징 처리
  String paging = pageUtil.getPaging(request.getContextPath() + "/blog/search.do", "&title=" + title + "&userEmail=" + userEmail + "&userName=" + userName + "&contents=" + contents + "&beginDt=" + beginDt + "&endDt=" + endDt);
  
  // 게시글 시작 번호
  int offset = pageUtil.getOffset();
  
  // 결과 목록과 갯수 반환하기
  return Map.of("searchList", searchList, "searchCount", searchCount, "paging", paging, "offset", offset);
}


@Override
public int increaseBlogHit(int blogId) {
  return blogDao.updateHit(blogId);
}

@Override
public String removeSelectBlog(String[] blogIds) {
  //  numbers 배열의 길이(삭제하려는 블로그 ID의 개수)가 같으면, 선택 삭제 성공
  return blogDao.deleteSelectBlog(blogIds) == blogIds.length ? "선택 삭제 성공" : "선택 삭제 실패";
}


}
