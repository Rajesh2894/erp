--liquibase formatted sql
--changeset nilima:V20171123181112__CR_TB_WORKTIME_MAS_01112017.sql
CREATE TABLE TB_WORKTIME_MAS (
  WR_ID BIGINT(12) NOT NULL COMMENT '',
  WR_ENTRY_DATE DATETIME NOT NULL COMMENT 'Entry Date',
  WR_START_TIME TIME(6) NOT NULL COMMENT 'Start Time',
  WR_END_TIME TIME(6) NOT NULL COMMENT 'End Time',
  WR_WEEK_TYPE BIGINT(12) NOT NULL COMMENT 'Week Off Type',
  WR_WORK_WEEK VARCHAR(50) NULL COMMENT 'Working Week',
  WR_ODD_WORK_WEEK VARCHAR(50) NULL COMMENT 'Odd Working Week',
  WR_EVEN_WORK_WEEK VARCHAR(50) NULL COMMENT 'Even Working Week',
  WR_VALID_END_DATE DATETIME NULL COMMENT 'Validate End Date',
  ORGID BIGINT(12) NULL COMMENT 'Organisation',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'record creation user',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'user id who updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WR_ID)  COMMENT '');

  
  