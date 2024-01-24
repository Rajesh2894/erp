--liquibase formatted sql
--changeset nilima:V20180626120440__CR_TB_WMS_TERMSCONDITION_25062018.sql
CREATE TABLE TB_WMS_TERMSCONDITION (
  TM_TEID bigint(12) NOT NULL COMMENT 'Primary key',
  WORK_ID bigint(12) NOT NULL COMMENT 'Work',
  TM_TERMDESC varchar(1000) NOT NULL COMMENT 'Terms Description',
  TM_TERMACTIVE char(1) NOT NULL COMMENT 'Active->Y,Inactive->N',
  REF_ID varchar(50) NOT NULL COMMENT 'Reference Id',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TM_TEID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Approval Terms and condition';