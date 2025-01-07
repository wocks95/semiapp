package com.min.semiapp.dao.Impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.min.semiapp.dao.IUserDao;
import com.min.semiapp.dto.LoginDto;
import com.min.semiapp.dto.UserDto;
import com.min.semiapp.dto.WithdrawalDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserDaoImpl implements IUserDao {

  private final SqlSessionTemplate template;
  
  @Override
  public int insertUser(UserDto userDto) {
    return template.insert("mybatis.mappers.userMapper.insertUser", userDto);
  }
  
  @Override
  public UserDto selectUserByMap(Map<String, Object> map) {
    return template.selectOne("mybatis.mappers.userMapper.selectUserByMap", map);
  }

  @Override
  public int updateUserInfo(UserDto userDto) throws Exception {
    return template.update("mybatis.mappers.userMapper.updateUserInfo", userDto);
  }
  
  @Override
  public int updateUserProfile(UserDto userDto) {
    return template.update("mybatis.mappers.userMapper.updateUserProfile", userDto);
  }
  
  @Override
  public int updateUserPassword(UserDto userDto) {
    return template.update("mybatis.mappers.userMapper.updateUserPassword", userDto);
  }
  
  @Override
  public int deleteUser(int userId) {
    return template.delete("mybatis.mappers.userMapper.deleteUser", userId);
  }
  
  @Override
  public int insertLogin(Map<String, Object> map) {
    return template.insert("mybatis.mappers.userMapper.insertLogin", map);
  }
  
  @Override
  public List<WithdrawalDto> selectWithdrawalList(Map<String, Object> map) {
    return template.selectList("mybatis.mappers.userMapper.selectWithdrawalList", map);
  }
  
  @Override
  public int selectWithdrawalCount() {
    return template.selectOne("mybatis.mappers.userMapper.selectWithdrawalCount");
  }
  
  @Override
  public List<LoginDto> selectLoginList(Map<String, Object> map) {
    return template.selectList("mybatis.mappers.userMapper.selectLoginList", map);
  }
  
  @Override
  public int selectLoginCount() {
    return template.selectOne("mybatis.mappers.userMapper.selectLoginCount");
  }
}
