--liquibase formatted sql
--changeset nilima:V20180609170300__CR_TB_SW_CONTVEND_MAPPING_HIST_09062018.sql
CREATE TABLE TB_SW_CONTVEND_MAPPING_HIST (
  MAP_ID_H bigint(12) NOT NULL,
  MAP_ID bigint(12) DEFAULT NULL,
  CONT_ID bigint(12) DEFAULT NULL COMMENT 'FK tb_contract_mast',
  MAP_TASK_ID varchar(50) DEFAULT NULL COMMENT 'Task Mapping',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE bigint(12) DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (MAP_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
