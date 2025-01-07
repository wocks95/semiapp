package com.min.semiapp.dao;

import java.util.List;
import java.util.Map;

import com.min.semiapp.dto.LoginDto;
import com.min.semiapp.dto.UserDto;
import com.min.semiapp.dto.WithdrawalDto;

public interface IUserDao {
  int insertUser(UserDto userDto);
  UserDto selectUserByMap(Map<String, Object> map);
  int updateUserInfo(UserDto userDto) throws Exception;
  int updateUserProfile(UserDto userDto);
  int updateUserPassword(UserDto userDto);
  int deleteUser(int userId);
  int insertLogin(Map<String, Object> map);
  List<WithdrawalDto> selectWithdrawalList(Map<String, Object> map);
  int selectWithdrawalCount();
  List<LoginDto> selectLoginList(Map<String, Object> map);
  int selectLoginCount();
}
