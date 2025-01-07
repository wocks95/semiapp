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
public class BbsDto {
  private int bbsId;
  private int userId;
  private String contents;
  private int state;
  private int depth;
  private int groupId;
  private int groupOrder;
  private Timestamp modifyDt;
  private Timestamp createdDt;
  private String userName;
}
