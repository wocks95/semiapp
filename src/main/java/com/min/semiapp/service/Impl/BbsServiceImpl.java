package com.min.semiapp.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.min.semiapp.dao.IBbsDao;
import com.min.semiapp.dto.BbsDto;
import com.min.semiapp.service.IBbsService;
import com.min.semiapp.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BbsServiceImpl implements IBbsService {

  private final IBbsDao bbsDao;
  private final PageUtil pageUtil;
  
  @Override
  public String registBbs(BbsDto bbsDto) {
    return bbsDao.insertBbs(bbsDto) == 1 ? "게시글 등록 성공" : "게시글 등록 실패";
  }
  
  @Override
  public Map<String, Object> getBbsList(HttpServletRequest request) {
    
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));

    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("5"));
    
    int count = bbsDao.selectBbsCount();
    
    pageUtil.setPaging(page, display, count);
    
    int offset = pageUtil.getOffset();
    
    List<BbsDto> bbsList = bbsDao.selectBbsList(Map.of("offset", offset, "display", display));
    
    String paging = pageUtil.getPaging(request.getContextPath() + "/bbs/list.do", "");
    
    return Map.of("offset", offset
                , "count", count
                , "bbsList", bbsList
                , "paging", paging);
    
  }
  
  @Override
  public String registBbsReply(BbsDto bbsDto) {

    /*
     * 파라미터 BbsDto bbsDto는 아래 값을 가지고 있습니다.
     *   contents   : 댓글의 내용
     *   depth      : 원글의 depth
     *   groupId    : 원글의 groupId
     *   groupOrder : 원글의 groupOrder
     */
    
    // 1. 기존 댓글의 group_order 업데이트
    bbsDao.updateGroupOrder(bbsDto);
    
    // 2. 댓글 등록
    bbsDto.setDepth(bbsDto.getDepth() + 1);            // 댓글의 depth는 원글의 depth 보다 1 큽니다.
    bbsDto.setGroupId(bbsDto.getGroupId());            // 댓글의 groupId는 원글의 groupId와 같습니다. 이 코드는 설명을 위한 코드일 뿐 작성하지 않습니다.
    bbsDto.setGroupOrder(bbsDto.getGroupOrder() + 1);  // 댓글의 groupOrder는 원글의 groupOrder 보다 1 큽니다.
    
    return bbsDao.insertBbsReply(bbsDto) == 1 ? "댓글 등록 성공" : "댓글 등록 실패";
    
  }
  
  @Override
  public String deleteBbs(int bbsId) {
    return bbsDao.deleteBbs(bbsId) == 1 ? "게시글 삭제 성공" : "게시글 삭제 실패";
  }

  @Override
  public Map<String, Object> getSearchList(HttpServletRequest request) {
    // 검색에 필요한 파라미터
    String contents = request.getParameter("contents");
    String userName = request.getParameter("userName");
    String beginDt = request.getParameter("beginDt");
    String endDt = request.getParameter("endDt");
    
    // 페이징 처리에 필요한 파라미터
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));

    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("5"));
    
    // 검색 키워드들을 Map으로 만듬
    Map<String, Object> map = new HashMap<String, Object>();  // Map.of()는 나중에 값을 추가할 수 없으므로 new HashMap()을 활용합니다.
    map.put("contents", contents);
    map.put("userName", userName);
    map.put("beginDt", beginDt);
    map.put("endDt", endDt);
    
    // 검색 결과 개수
    int searchCount = bbsDao.selectSearchCount(map);
    
    // 페이징 처리에 필요한 모든 변수 처리하기
    pageUtil.setPaging(page, display, searchCount);
    int offset = pageUtil.getOffset();
    
    // 검색키워드 Map에 페이징 처리에 필요한 변수를 추가
    map.put("offset", offset);
    map.put("display", display);
    
    // 검색 목록 가져오기
    List<BbsDto> searchList = bbsDao.selectSearchList(map);
    
    // 페이지 이동 링크 가져오기
    String paging = pageUtil.getSearchPaging(request.getContextPath() + "/bbs/search.do", "contents=" + contents + "&userName=" + userName + "&beginDt=" + beginDt + "&endDt=" + endDt);
    
    // 결과 반환
    return Map.of("searchList", searchList
                , "searchCount", searchCount
                , "paging", paging
                , "offset", offset);  // offset 으로 순번 생성;
  }
  
  
}
