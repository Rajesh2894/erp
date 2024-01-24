--liquibase formatted sql
--changeset nilima:V20180918195530__CR_TB_LGL_COURT_MAST_18092018.sql
CREATE TABLE TB_LGL_COURT_MAST (
  CRT_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  CRT_TYPE bigint(12) NOT NULL COMMENT 'Court Type (CTP Prefix)',
  CRT_NAME varchar(200) NOT NULL COMMENT 'Court Name',
  CRT_NAME_REG varchar(200) NOT NULL COMMENT 'Court Name Reg.',
  CRT_START_TIME datetime NOT NULL COMMENT 'Court Start Time',
  CRT_END_TIME datetime NOT NULL COMMENT 'Court End Time',
  CRT_PHONE_NO varchar(50) NOT NULL COMMENT 'Court Phone No.',
  CRT_EMAIL_ID varchar(50) DEFAULT NULL,
  CRT_ADDRESS varchar(500) NOT NULL,
  CRT_STATUS char(1) NOT NULL COMMENT 'Court Status(Active->''Y'',''N'')',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (CRT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Court Master';




