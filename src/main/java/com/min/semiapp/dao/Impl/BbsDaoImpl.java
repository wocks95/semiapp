package com.min.semiapp.dao.Impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.min.semiapp.dao.IBbsDao;
import com.min.semiapp.dto.BbsDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BbsDaoImpl implements IBbsDao {
  
  private final SqlSessionTemplate template;
  
  @Override
  public int insertBbs(BbsDto bbsDto) {
    return template.insert("mybatis.mappers.bbsMapper.insertBbs", bbsDto);
  }
  
  @Override
  public List<BbsDto> selectBbsList(Map<String, Object> map) {
    return template.selectList("mybatis.mappers.bbsMapper.selectBbsList", map);
  }
  
  @Override
  public int selectBbsCount() {
    return template.selectOne("mybatis.mappers.bbsMapper.selectBbsCount");
  }
  
  @Override
  public int updateGroupOrder(BbsDto bbsDto) {
    return template.update("mybatis.mappers.bbsMapper.updateGroupOrder", bbsDto);
  }
  
  @Override
  public int insertBbsReply(BbsDto bbsDto) {
    return template.insert("mybatis.mappers.bbsMapper.insertBbsReply", bbsDto);
  }
  
  @Override
  public int deleteBbs(int bbsId) {
    return template.update("mybatis.mappers.bbsMapper.deleteBbs", bbsId);
  }

  @Override
  public int selectSearchCount(Map<String, Object> map) {
    return template.selectOne("mybatis.mappers.bbsMapper.selectSearchCount", map);
  }
  
  @Override
  public List<BbsDto> selectSearchList(Map<String, Object> map) {
    return template.selectList("mybatis.mappers.bbsMapper.selectSearchList", map);
  }
}
