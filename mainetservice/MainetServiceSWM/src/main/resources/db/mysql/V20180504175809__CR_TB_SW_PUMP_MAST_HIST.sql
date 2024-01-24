--liquibase formatted sql
--changeset nilima:V20180504175809__CR_TB_SW_PUMP_MAST_HIST.sql
CREATE TABLE TB_SW_PUMP_MAST_HIST (
  PU_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  PU_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  PU_PUTYPE bigint(12) DEFAULT NULL COMMENT 'Type of Pump from prefix',
  PU_PUMPNAME varchar(100) DEFAULT NULL COMMENT 'Pump Name',
  PU_ADDRESS varchar(200) DEFAULT NULL COMMENT 'Pump Address',
  LOC_ID bigint(12) DEFAULT NULL COMMENT 'Location Id',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (PU_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Refilling Pump Station Detail';
