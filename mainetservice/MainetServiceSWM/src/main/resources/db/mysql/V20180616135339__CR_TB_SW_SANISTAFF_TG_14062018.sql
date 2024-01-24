--liquibase formatted sql
--changeset nilima:V20180616135339__CR_TB_SW_SANISTAFF_TG_14062018.sql
CREATE TABLE TB_SW_SANISTAFF_TG (
  SAN_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  SAN_TYPE BIGINT(12) NOT NULL COMMENT 'Target Type',
  SAN_TGFROMDT DATE NOT NULL COMMENT 'Target From Date',
  SAN_TGTODT DATE NOT NULL COMMENT 'Target To Date',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SAN_ID))
COMMENT = 'SANITATION STAFF TARGET';