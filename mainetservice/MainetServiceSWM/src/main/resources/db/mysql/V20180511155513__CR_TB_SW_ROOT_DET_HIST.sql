--liquibase formatted sql
--changeset nilima:V20180511155513__CR_TB_SW_ROOT_DET_HIST.sql
DROP TABLE IF EXISTS TB_SW_ROOT_DET_HIST
--changeset nilima:V20180511155513__CR_TB_SW_ROOT_DET_HIST1.sql
CREATE TABLE TB_SW_ROOT_DET_HIST (
  ROD_ID_H bigint(12)  COMMENT 'Primary Key',
  ROD_ID bigint(12)  COMMENT 'Primary Key',
  RO_ID bigint(12)  COMMENT 'FK tb_sw_root_mast',
  RO_COLL_POINTNAME varchar(500)  COMMENT 'Collection Point Name',
  RO_COLL_POINTADD varchar(500)  COMMENT 'Collection Point Address',
  RO_COLL_LATITUDE varchar(100)  COMMENT 'Latitude',
  RO_COLL_LONGITUDE varchar(100)  COMMENT 'Logitude',
  RO_SEQ_NO bigint(12)  COMMENT 'Collection Point Sequence no.',
  RO_COLL_TYPE bigint(12)  COMMENT 'Collection Type',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (ROD_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Root Detail';