--liquibase formatted sql
--changeset nilima:V20181001150027__CR_TB_SW_MRF_VECHICLE_DET_HIST_25092018.sql
CREATE TABLE TB_SW_MRF_VECHICLE_DET_HIST (
  MRFV_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  MRFV_ID bigint(12)  COMMENT 'Primary key',
  VE_VETYPE bigint(12)  COMMENT 'Vechicle Type',
  MRFV_AVALCNT bigint(12)  COMMENT 'Vechicle Avalable Count',
  MRFV_REQCNT bigint(12) ,
  H_STATUS CHAR(1) COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MRFV_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;