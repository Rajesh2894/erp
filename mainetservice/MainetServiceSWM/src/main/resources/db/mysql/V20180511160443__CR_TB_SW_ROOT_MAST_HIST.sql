--liquibase formatted sql
--changeset nilima:V20180511160443__CR_TB_SW_ROOT_MAST_HIST.sql;
DROP TABLE IF EXISTS TB_SW_ROOT_MAST_HIST;
--liquibase formatted sql
--changeset nilima:V20180511160443__CR_TB_SW_ROOT_MAST_HIST1.sql;
CREATE TABLE TB_SW_ROOT_MAST_HIST (
  RO_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  RO_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  RO_NO varchar(50) DEFAULT NULL COMMENT 'Route Number',
  RO_NAME varchar(100) DEFAULT NULL COMMENT 'Route Name',
  RO_NAME_REG varchar(45) DEFAULT NULL COMMENT 'Route Name Regional',
  COD_WARD1 bigint(12) DEFAULT NULL COMMENT 'Ward',
  COD_WARD2 bigint(12) DEFAULT NULL COMMENT 'Zone',
  COD_WARD3 bigint(12) DEFAULT NULL COMMENT 'Block',
  COD_WARD4 bigint(12) DEFAULT NULL COMMENT 'route',
  COD_WARD5 bigint(12) DEFAULT NULL,
  RO_START_POINT bigint(12) DEFAULT NULL COMMENT 'Starting Point',
  RO_END_POINT bigint(12) DEFAULT NULL COMMENT 'End Point',
  RO_DISTANCE decimal(12,2) DEFAULT NULL COMMENT 'Total Route Distance',
  RO_DISTANCE_UNIT bigint(12) DEFAULT NULL COMMENT 'Total Route Distance Unit',
  RO_VE_TYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (RO_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;