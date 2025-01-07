package com.min.semiapp.dao;

import java.util.List;
import java.util.Map;

import com.min.semiapp.dto.BbsDto;

public interface IBbsDao {
  int insertBbs(BbsDto bbsDto);
  List<BbsDto> selectBbsList(Map<String, Object> map);
  int selectBbsCount();
  int updateGroupOrder(BbsDto bbsDto);
  int insertBbsReply(BbsDto bbsDto);
  int deleteBbs(int bbsId);
  int selectSearchCount(Map<String, Object> map);
  List<BbsDto> selectSearchList(Map<String, Object> map);
}
