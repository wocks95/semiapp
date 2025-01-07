package com.min.semiapp.service.Impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.min.semiapp.dao.ICommentDao;
import com.min.semiapp.dto.CommentDto;
import com.min.semiapp.service.ICommentService;
import com.min.semiapp.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements ICommentService {

  private final ICommentDao commentDao;
  private final PageUtil pageUtil;
  
  // 댓글 작성
  @Override
  public String registComment(CommentDto commentDto) {
    return commentDao.insertComment(commentDto) == 1? "댓글 작성 성공" : "댓글 작성 실패";
  }
  
  // 대댓글 작성
  @Override
  public String registCommentReply(CommentDto commentDto) {
    
    // 1. 기존 댓글 group_order 업데이트
    commentDao.updateGroupOrder(commentDto);
    
    // 2. 댓글 등록
    commentDto.setDepth(commentDto.getDepth() + 1);
    // commentDto.setGroupId(commentDto.getGroupId());
    commentDto.setGroupOrder(commentDto.getGroupOrder() + 1);

    return commentDao.insertCommentReply(commentDto) == 1 ? "대댓글 작성 성공" : "대댓글 작성 실패";
  }
  
  // 댓글 리스트 만들기
  @Override
  public Map<String, Object> getCommentList(HttpServletRequest request, int blogId) {
    
    // comment 리스트 만들기
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    int count = commentDao.selectCommentCount(blogId);
    
    pageUtil.setPaging(page, display, count);
    
    int offset = pageUtil.getOffset();
    
    List<CommentDto> commentList = commentDao.selectCommentList(Map.of("offset", offset, "display", display, "blogId", blogId));
    
    String paging = pageUtil.getPaging(request.getContextPath() + "/blog/detail.do", "&blogId=" + blogId);
    
    return Map.of("offset", offset, "count", count, "commentList", commentList, "paging", paging);
    
  }

  // 댓글 삭제
  @Override
  public String deleteCommentReply(int commentId) {
    return commentDao.deleteComment(commentId) == 1? "댓글 삭제 성공" : "댓글 삭제 실패";
  }
  
}
