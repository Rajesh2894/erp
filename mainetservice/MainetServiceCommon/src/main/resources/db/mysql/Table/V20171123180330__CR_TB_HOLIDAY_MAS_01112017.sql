--liquibase formatted sql
--changeset nilima:V20171123180330__CR_TB_HOLIDAY_MAS_01112017.sql
 CREATE TABLE TB_HOLIDAY_MAS (
  HO_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  HO_YEAR_START_DATE date NOT NULL COMMENT 'Calender Year Start Date',
  HO_YEAR_END_DATE date NOT NULL COMMENT 'Calender Year End Date',
  HO_DATE date DEFAULT NULL COMMENT 'HOLIDAY DATE',
  HO_DESCRIPTION varchar(100) NOT NULL COMMENT 'Holiday Description',
  ORGID bigint(12) DEFAULT NULL COMMENT 'Organisation',
  CREATED_BY bigint(12) NOT NULL COMMENT 'record creation user',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  HO_ACTIVE varchar(1) DEFAULT 'Y' COMMENT 'Active/Inactive(Y,N)',
  PRIMARY KEY (HO_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;