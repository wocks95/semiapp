package com.min.semiapp.dao.Impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.min.semiapp.dao.INoticeDao;
import com.min.semiapp.dto.AttachDto;
import com.min.semiapp.dto.NoticeDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class NoticeDaoImpl implements INoticeDao {

  private final SqlSessionTemplate template; 
  
  
  @Override
  public List<NoticeDto> selectNoticeList(Map<String, Object> map) { //전체보기
    return template.selectList("mybatis.mappers.noticeMapper.selectNoticeList", map);
  }

  @Override
  public int selectNoticeCount() {
    return template.selectOne("mybatis.mappers.noticeMapper.selectNoticeCount");
  }
  
  @Override
  public NoticeDto selectNoticeById(int noticeId) {
    return template.selectOne("mybatis.mappers.noticeMapper.selectNoticeById", noticeId);
  }

  @Override
  public List<AttachDto> selectAttachListByNoticeId(int noticeId) {    
    return template.selectList("mybatis.mappers.noticeMapper.selectAttachListByNoticeId", noticeId);
  }

  @Override
  public AttachDto selectAttachById(int attachId) {
    return template.selectOne("mybatis.mappers.noticeMapper.selectAttachById", attachId);
  }

  @Override
  public int insertNotice(NoticeDto noticeDto) {
    return template.insert("mybatis.mappers.noticeMapper.insertNotice", noticeDto);
  }

  @Override
  public int insertAttach(AttachDto attachDto) {
    return template.insert("mybatis.mappers.noticeMapper.insertAttach", attachDto);
  }

  @Override
  public int deleteNotice(int noticeId) {
    return template.delete("mybatis.mappers.noticeMapper.deleteNotice", noticeId);
  }
  
  @Override
  public int deleteAttach(String[] attachIds) {
    return template.delete("mybatis.mappers.noticeMapper.deleteAttach", attachIds);
  }

  @Override
  public int updateAttachDownloadCount(int attachId) {
    return template.update("mybatis.mappers.noticeMapper.updateAttachDownloadCount", attachId);
  }

  @Override
  public List<NoticeDto> selectSearchList(Map<String, Object> map) {
    return template.selectList("mybatis.mappers.noticeMapper.selectSearchList", map);
  }
  
  @Override
  public int selectSearchCount(Map<String, Object> map) {
    return template.selectOne("mybatis.mappers.noticeMapper.selectSearchCount", map);
  }
  
  @Override
  public int modifyNotice(NoticeDto noticeDto) {
    return template.update("mybatis.mappers.noticeMapper.modifyNotice", noticeDto);
  }
  
  @Override
  public int modifyAttach(AttachDto attachDto) {
    return template.update("mybatis.mappers.noticeMapper.modifyAttach", attachDto);
  }
  
  
}
