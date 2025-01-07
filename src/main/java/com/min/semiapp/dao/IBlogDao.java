package com.min.semiapp.dao;

import java.util.List;
import java.util.Map;

import com.min.semiapp.dto.BlogDto;

public interface IBlogDao {

  // 목록
  List<BlogDto> selectBlogList(Map<String, Object> map);
  
  // 목록 개수
  int selectBlogCount();
  
  // 블로그 추가
  int insertBlog(BlogDto blogDto);
  
  // 블로그 아이디 조회
  BlogDto selectBlogById(int blogId);
  
  // 블로그 수정
  int updateBlog(BlogDto blogDto);
  
  // 블로그 삭제
  int deleteBlog(int blogId);
  
  // 블로그 검색
  List<BlogDto> selectBlogSearchList(Map<String, Object> map);
  int selectBlogSearchCount(Map<String, Object> map);
  
  // 블로그 조회수 증가
  int updateHit(int blogId);
  
  // 블로그 선택 삭제
  int deleteSelectBlog(String[] blogIds);
}
