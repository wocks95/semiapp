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
public class BlogDto {
  private int blogId;
  private String title;
  private String contents;
  private int hit;
  private Timestamp modifyDt;
  private Timestamp createDt;
  private UserDto userDto;
}
