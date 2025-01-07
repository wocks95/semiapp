package com.min.semiapp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.min.semiapp.dto.UserDto;

public interface IUserService {
  String signup(UserDto userDto);
  int login(HttpServletRequest request);
  void logout(HttpSession session);
  UserDto mypage(int userId);
  String modifyInfo(UserDto userDto) throws Exception;
  String modifyProfile(MultipartFile profile, int userId) throws Exception;
  String modifyPw(UserDto userDto);
  String deleteAccount(int userId);  
  Map<String, Object> getWithdrawalList(HttpServletRequest request);
  Map<String, Object> getLoginList(HttpServletRequest request);  
}
