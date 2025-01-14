package com.min.semiapp.service.Impl;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.min.semiapp.dao.INoticeDao;
import com.min.semiapp.dto.AttachDto;
import com.min.semiapp.dto.NoticeDto;
import com.min.semiapp.dto.UserDto;
import com.min.semiapp.service.INoticeService;
import com.min.semiapp.util.FileUtil;
import com.min.semiapp.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements INoticeService {

  private final INoticeDao noticeDao;
  private final FileUtil fileUtil;
  private final PageUtil pageUtil;
  
  @Override
  public Map<String, Object> getNoticeList(HttpServletRequest request) {
    // 페이징 처리를 위한 파라미터 page, display
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("5"));
    
    // 페이징 처리를 위한 전체 공지 개수
    int total = noticeDao.selectNoticeCount();
    
    // 페이징 처리에 필요한 모든 변수 처리하기
    pageUtil.setPaging(page, display, total);
    int offset = pageUtil.getOffset();
    
    // 정렬을 위한 파라미터 sort, column
    Optional<String> optsort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optsort.orElse("Desc");
    
    Optional<String> optcolumn = Optional.ofNullable(request.getParameter("column"));
    String column = optcolumn.orElse("notice_id");
    
    // 목록 가져오기
    List<NoticeDto> noticeList = noticeDao.selectNoticeList(Map.of("offset", offset, "display", display, "sort", sort, "column", column));
    
    // 페이지 이동 링크 가져오기
    String paging = pageUtil.getPaging(request.getContextPath() + "/notice/list.do", sort, column);
    
    // 결과 반환
    return Map.of("noticeList", noticeList
                , "total", total
                , "paging", paging
                , "offset", offset);
  }

  @Override
  public String registNotice(MultipartHttpServletRequest mutlipartRequest) {
    String title = mutlipartRequest.getParameter("title");
    String contents = mutlipartRequest.getParameter("contents");
    int userId = Integer.parseInt(mutlipartRequest.getParameter("userId"));

    NoticeDto noticeDto = NoticeDto.builder()
                              .title(title)
                              .contents(contents)
                              .userDto(UserDto.builder()
                                          .userId(userId)
                                          .build())
                              .build();
    
    int result = noticeDao.insertNotice(noticeDto);
    if(result == 0)
       return "공지사항 등록 실패";
    // ------여기까지 왔다는건 공지사항 등록 성공했다는 의미이다.------
    
    List<MultipartFile> files = mutlipartRequest.getFiles("files");
    for(MultipartFile multipartFile : files) {
      // 첨부 파일 존재 여부 확인
      if(!multipartFile.isEmpty()) { 
        String originalFilename = multipartFile.getOriginalFilename(); // 첨부 파일의 원래 이름
        String filesystemName = fileUtil.getFilesystemName(originalFilename); // 첨부 파일의 저장 이름
        String filePath = fileUtil.getFilePath(); // 첨부 파일의 저장 경로
        File dir = new File(filePath);
        if(!dir.exists())
          dir.mkdirs();
        
        try {
          multipartFile.transferTo(new File(dir, filesystemName));
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        AttachDto attachDto = AttachDto.builder()
                                  .noticeId(noticeDto.getNoticeId())
                                  .filePath(filePath)
                                  .originalFilename(originalFilename)
                                  .filesystemName(filesystemName)
                                  .build();
        int attachResult = noticeDao.insertAttach(attachDto);
        if(attachResult == 0)
          return "첨부 파일 등록 실패";
      }
    }
    return "공지사항 등록 성공";
  }

  @Override
  public Map<String, Object> getNoticeById(int noticeId) {
    return Map.of("n", noticeDao.selectNoticeById(noticeId)
                , "attachList", noticeDao.selectAttachListByNoticeId(noticeId));
  }

  @Override
  public String removeNotice(int noticeId) {
    
    for(AttachDto attachDto : noticeDao.selectAttachListByNoticeId(noticeId)) {
      File file = new File(attachDto.getFilePath(), attachDto.getFilesystemName());
      if(file.exists())
        file.delete(); 
    }
    int result = noticeDao.deleteNotice(noticeId);
    return result == 1 ? "공지사항 삭제 성공" : "공지사항 삭제 실패";
  }

  @Override
  public ResponseEntity<Resource> download(int attachId, String userAgent) {
    AttachDto attachDto = noticeDao.selectAttachById(attachId);
    
    Resource resource = new FileSystemResource(new File(attachDto.getFilePath(), attachDto.getFilesystemName()));
    
    if(! resource.exists())
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    
    String originalFilename = attachDto.getOriginalFilename();
    try {
      if(userAgent.contains("Edg")) {
        originalFilename  = URLEncoder.encode(originalFilename, "UTF-8");
      }
      else {
        originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1");
      } 
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    HttpHeaders responseHeader = new HttpHeaders();
    
    try {
      responseHeader.add("content-Disposition", "attachment; filename=" + originalFilename);
      responseHeader.add("Content_Type", "application/octet-stream");
      responseHeader.add("Content-Length", resource.contentLength() + "");  
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    noticeDao.updateAttachDownloadCount(attachId);
    
    
    return new ResponseEntity<Resource>(resource, responseHeader, HttpStatus.OK);
  }

  @Override
  public Map<String, Object> getSearchList(HttpServletRequest request) {
    // 검색에 필요한 파라미터
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    String beginDt = request.getParameter("beginDt");
    String endDt = request.getParameter("endDt");
    
    // 페이징 처리에 필요한 파라미터
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("5"));
    
    // 검색 키워드들을 Map으로 만듬
    Map<String, Object> map = new HashMap<String, Object>();  // Map.of()는 나중에 값을 추가할 수 없으므로 new HashMap()을 활용합니다.
    map.put("title", title);
    map.put("contents", contents);
    map.put("beginDt", beginDt);
    map.put("endDt", endDt);
    
    // 검색 결과 개수
    int searchCount = noticeDao.selectSearchCount(map);
    
    // 페이징 처리에 필요한 모든 변수 처리하기
    pageUtil.setPaging(page, display, searchCount);
    int offset = pageUtil.getOffset();
    
    // 검색키워드 Map에 페이징 처리에 필요한 변수를 추가
    map.put("offset", offset);
    map.put("display", display);
    
    // 검색 목록 가져오기
    List<NoticeDto> searchList = noticeDao.selectSearchList(map);
    
    // 페이지 이동 링크 가져오기
    String paging = pageUtil.getSearchPaging(request.getContextPath() + "/notice/search.do", "title=" + title + "&contents=" + contents + "&beginDt=" + beginDt + "&endDt=" + endDt);
    
    // 결과 반환
    return Map.of("searchList", searchList
                , "searchCount", searchCount
                , "paging", paging
                , "offset", offset); //offset으로 순번 생성;
  }

  @Override
  public String modifyNotice(NoticeDto noticeDto, MultipartHttpServletRequest mutlipartRequest) {
    
    int result = noticeDao.modifyNotice(noticeDto);
    if(result == 0)
       return "공지사항 수정 실패";
    
    List<MultipartFile> files = mutlipartRequest.getFiles("files");
    for(MultipartFile multipartFile : files) {
      // 첨부 파일 존재 여부 확인
      if(!multipartFile.isEmpty()) { 
        String originalFilename = multipartFile.getOriginalFilename(); // 첨부 파일의 원래 이름
        String filesystemName = fileUtil.getFilesystemName(originalFilename); // 첨부 파일의 저장 이름
        String filePath = fileUtil.getFilePath(); // 첨부 파일의 저장 경로
        File dir = new File(filePath);
        if(!dir.exists())
          dir.mkdirs();
        
        try {
          multipartFile.transferTo(new File(dir, filesystemName));
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        AttachDto attachDto = AttachDto.builder()
                                  .noticeId(noticeDto.getNoticeId())
                                  .filePath(filePath)
                                  .originalFilename(originalFilename)
                                  .filesystemName(filesystemName)
                                  .build();
        int attachResult = noticeDao.insertAttach(attachDto);
        if(attachResult == 0)
          return "첨부 파일 등록 실패";
      }
    }
    return "공지사항 수정 성공";    
  }

  @Override
  public String removeAttach(String[] attachIds) {
    
    return noticeDao.deleteAttach(attachIds) == attachIds.length ? "삭제 성공" : "삭제 실패";
  }
  
}
