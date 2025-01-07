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
public class NoticeDto {
  private int noticeId;
  private UserDto userDto;
  private String title;
  private String contents;
  private Timestamp modifyDt;
  private Timestamp createDt;
  private int attachCount;
}
