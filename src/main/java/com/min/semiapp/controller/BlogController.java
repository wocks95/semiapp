package com.min.semiapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.min.semiapp.dto.BlogDto;
import com.min.semiapp.dto.CommentDto;
import com.min.semiapp.service.IBlogService;
import com.min.semiapp.service.ICommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BlogController {

  private final IBlogService blogService;
  private final ICommentService commentService;
  
  // 블로그 작성 페이지 연결
  @RequestMapping(value="/blog/write.do")
  public String write() {
    return "/blog/write";
  }
  
  // 블로그 추가 성공 여부 메시지 반환
  @RequestMapping(value="/blog/regist.do", method=RequestMethod.POST)
  public String registBlog(BlogDto blogDto, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", blogService.registBlog(blogDto));
    return "redirect:/blog/list.do";
  }
  
  // 블로그 상세 내용 확인
  @RequestMapping(value="/blog/detail.do")
  public String detail(HttpServletRequest request, @RequestParam(value="blogId") int blogId, Model model) {
    
    // 블로그 상세 내용 detail.jsp에 전달 
    model.addAttribute("blog", blogService.getBlogById(blogId));
    
    // 블로그 상세에 댓글 리스트 함께 노출 (블로그 아이디 별로 댓글 리스트 달라짐)
    Map<String, Object> map  = commentService.getCommentList(request, blogId);
    model.addAttribute("offset", map.get("offset"));
    model.addAttribute("count", map.get("count"));
    model.addAttribute("commentList", map.get("commentList"));
    model.addAttribute("paging", map.get("paging"));
    
    return "/blog/detail";
  }
  
  // 블로그 댓글 최초 작성 (블로그 아이디 별로 댓글을 작성한다.)
  @RequestMapping(value="/blog/registComment.do", method=RequestMethod.POST)
  public String registComment(CommentDto commentDto, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", commentService.registComment(commentDto));
    return "redirect:/blog/detail.do?blogId=" + commentDto.getBlogId();
  }
  
  // 블로그 대댓글 작성
  @RequestMapping(value="/blog/registCommentReply.do", method=RequestMethod.POST)
  public String registCommentReply(CommentDto commentDto, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", commentService.registCommentReply(commentDto));
    return "redirect:/blog/detail.do?blogId=" + commentDto.getBlogId();
  }
  
  // 블로그 댓글 삭제
  @RequestMapping(value="blog/deleteCommentReply.do")
  public String delete(@RequestParam(name = "blogId",required = false, defaultValue = "0") int blogId,
                       @RequestParam(name = "commentId",required = false, defaultValue = "0") int commentId,
                       RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", commentService.deleteCommentReply(commentId));
    return "redirect:/blog/detail.do?blogId=" + blogId;
  }
  
  
  // 블로그 수정 성공 여부 메시지 반환
  @RequestMapping(value="/blog/modify.do", method=RequestMethod.POST)
  public String modify(BlogDto blogDto, RedirectAttributes redirectAttributes) {
    // 수정 후 결과 메시지를 list.do에 요청하면서 전달
    redirectAttributes.addFlashAttribute("msg", blogService.modifyBlog(blogDto));
    return "redirect:/blog/list.do";
  }
  
  // 블로그 삭제 성공 여부 메시지 반환
  @RequestMapping(value="/blog/remove.do", method=RequestMethod.POST)
  public String remove(@RequestParam(value="blogId") int blogId, RedirectAttributes redirectAttributes) {
   redirectAttributes.addFlashAttribute("msg", blogService.removeBlog(blogId));
   return "redirect:/blog/list.do";
  }
  
  // 블로그 목록 및 페이징 처리
  @RequestMapping(value="/blog/list.do")
  public String list(HttpServletRequest request, Model model) {
    Map<String, Object> map = blogService.getBlogList(request);
    model.addAttribute("blogs", map.get("blogs"));
    model.addAttribute("total", map.get("total"));
    model.addAttribute("paging", map.get("paging"));
    model.addAttribute("offset", map.get("offset"));
    return "blog/list";
  }
  
  // 블로그 검색
  @RequestMapping(value="/blog/search.do")
  public String search(HttpServletRequest request, Model model) {
    // 검색 서비스로부터 검색 결과 반환
    Map<String, Object> map = blogService.getSearchList(request);
    // 검색 결과 목록과 갯수를 jsp로 전달할 수 있도록 model에 저장
    model.addAttribute("blogs", map.get("searchList"));
    model.addAttribute("total", map.get("searchCount"));
    model.addAttribute("paging", map.get("paging"));
    model.addAttribute("offset", map.get("offset"));
    model.addAttribute("sort", map.get("sort"));
    model.addAttribute("display", map.get("display"));
    model.addAttribute("offset", map.get("offset"));
    return "blog/list";
  }
  
  // 블로그 조회수 증가
  @RequestMapping(value="/blog/increaseBlogHit.do")
  public String increaseBlogHit(@RequestParam(value="blogId", required=false, defaultValue="0") int blogId) {
    String path = blogService.increaseBlogHit(blogId) == 1 ? "/detail.do?blogId=" + blogId : "/list.do";
    return "redirect:/blog" + path;
  }
  
  // 블로그 선택 삭제
  @RequestMapping(value="/blog/removes.do", method=RequestMethod.POST)
  public String removes(String[] blogIds, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("msg", blogService.removeSelectBlog(blogIds));
    return "redirect:/blog/list.do";
  }
  

}
