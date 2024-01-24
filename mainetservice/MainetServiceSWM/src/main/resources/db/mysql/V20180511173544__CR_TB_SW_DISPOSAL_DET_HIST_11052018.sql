--liquibase formatted sql
--changeset nilima:V20180511154721__CR_TB_SW_DISPOSAL_MAST_11052018.sql
CREATE TABLE TB_SW_DISPOSAL_DET_HIST (
  DED_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  DED_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  DE_ID bigint(12) DEFAULT NULL COMMENT 'foregin key TB_SW_DEPOSAL_MAST',
  DE_WEST_TYPE bigint(12) DEFAULT NULL COMMENT 'Desposal Waste Details',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (DED_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Desposal Details';