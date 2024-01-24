--liquibase formatted sql
--changeset nilima:V20180504175616__CR_TB_SW_PUMP_FULDET_HIST.sql
CREATE TABLE TB_SW_PUMP_FULDET_HIST (
  PFU_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  PFU_ID bigint(12) DEFAULT NULL COMMENT 'Primary key',
  PU_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_PUMP_MST',
  PU_FUID bigint(12) DEFAULT NULL COMMENT ' Fuel Type->Value from prefix',
  PU_FUUNIT bigint(12) DEFAULT NULL COMMENT ' Fuel Unit->Value from prefix',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (PFU_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Refillin pump station detail';
