package com.min.semiapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.min.semiapp.dto.BlogDto;

public interface IBlogService {

  // 목록 및 페이징 처리
  Map<String, Object> getBlogList(HttpServletRequest request);
  
  // 블로그 추가
  String registBlog(BlogDto blogDto);
  
  // 블로그 아이디 조회
  BlogDto getBlogById(int blogId);
  
  // 블로그 수정
  String modifyBlog(BlogDto blogDto);
  
  // 블로그 삭제
  String removeBlog(int blogId);
  
  // 블로그 검색
  Map<String, Object> getSearchList(HttpServletRequest request);
  
  // 조회수 증가
  int increaseBlogHit(int blogId);
  
  // 블로그 선택 삭제
  String removeSelectBlog(String[] blogIds);

}
