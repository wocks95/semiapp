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
public class CommentDto {
  private int commentId;
  private UserDto userDto;
  private int blogId;
  private String contents;
  private Timestamp modifyDt;
  private Timestamp createDt;
  private int state;
  private int depth;
  private int groupId;
  private int groupOrder;
}
