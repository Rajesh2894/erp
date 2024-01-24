--liquibase formatted sql
--changeset nilima:V20180616142118__CR_TB_SW_SANISTAFF_TG_HIST_14062018.sql
CREATE TABLE TB_SW_SANISTAFF_TG_HIST (
  SAN_ID_H BIGINT(12) NOT NULL COMMENT 'Primary Key',
  SAN_ID BIGINT(12)  COMMENT 'Primary Key',
  SAN_TYPE BIGINT(12)  COMMENT 'Target Type',
  SAN_TGFROMDT DATE  COMMENT 'Target From Date',
  SAN_TGTODT DATE  COMMENT 'Target To Date',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID BIGINT(12)  COMMENT 'organization id',
  CREATED_BY BIGINT(12)  COMMENT 'user id who created the record',
  CREATED_DATE DATETIME  COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SAN_ID_H))
COMMENT = 'SANITATION STAFF TARGET History';