--liquibase formatted sql
--changeset nilima:V20180629200510__CR_TB_SW_DGARBAGE_COL_28062018.sql
CREATE TABLE TB_SW_DGARBAGE_COL(
  DG_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  REGISTRATION_ID BIGINT(12) NOT NULL COMMENT 'FK tb_sw_registration',
  DGC_DATE VARCHAR(45) NOT NULL COMMENT 'Door to Door Garbage Collection date',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (DG_ID));
