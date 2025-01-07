package com.min.semiapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.min.semiapp.dto.BbsDto;

public interface IBbsService {
  String registBbs(BbsDto bbsDto);
  Map<String, Object> getBbsList(HttpServletRequest request);
  String registBbsReply(BbsDto bbsDto);
  String deleteBbs(int bbsId);
  Map<String, Object> getSearchList(HttpServletRequest request);

}
