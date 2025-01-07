package com.min.semiapp.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {
  private int userId;
  private String userPw;
  private String userEmail;
  private String userName;
  private String profileImg;
  private String sessionId;
  private int isAdmin;
  private Timestamp changeDt;
  private Timestamp createDt; 
}
