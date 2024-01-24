--liquibase formatted sql
--changeset nilima:V20180609165546__CR_TB_SW_TRIPSHEET_GDET_HIST_08062018.sql
CREATE TABLE TB_SW_TRIPSHEET_GDET_HIST(
  TRIPD_ID_H BIGINT(12) NOT NULL COMMENT 'Primary Key',
  TRIPD_ID BIGINT(12)  COMMENT 'Primary Key',
  TRIP_ID BIGINT(12)  COMMENT 'FK TB_SW_TRIPSHEET',
  WAST_TYPE BIGINT(12)  COMMENT 'Waste Type',
  TRIP_VOLUME BIGINT(20)  COMMENT 'Volume',
  ORGID BIGINT(12)  COMMENT 'organization id',
  H_STATUS char(1)   COMMENT 'Status I->Insert,update',  
  CREATED_BY BIGINT(12)  COMMENT 'user id who created the record',
  CREATED_DATE DATETIME  COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME  COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TRIPD_ID_H));