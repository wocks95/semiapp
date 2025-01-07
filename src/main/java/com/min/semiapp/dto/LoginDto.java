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
public class LoginDto {
  private int userId;
  private String userEmail;
  private String userName;  
  private Timestamp accDt;
  private String accIp;
  private String userAgent;
}
