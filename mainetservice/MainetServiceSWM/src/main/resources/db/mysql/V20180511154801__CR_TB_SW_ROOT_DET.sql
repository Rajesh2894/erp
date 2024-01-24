--liquibase formatted sql
--changeset nilima:V20180511154801__CR_TB_SW_ROOT_DET.sql
DROP TABLE IF EXISTS TB_SW_ROOT_DET; 
--changeset nilima:V20180511154801__CR_TB_SW_ROOT_DET1.sql
CREATE TABLE TB_SW_ROOT_DET (
  ROD_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  RO_ID BIGINT(12) NULL COMMENT 'FK tb_sw_root_mast',
  RO_COLL_POINTNAME VARCHAR(500) NULL COMMENT 'Collection Point Name',
  RO_COLL_POINTADD VARCHAR(500) NULL COMMENT 'Collection Point Address',
  RO_COLL_LATITUDE VARCHAR(100) NULL COMMENT 'Latitude',
  RO_COLL_LONGITUDE VARCHAR(100) NULL COMMENT 'Logitude',
  RO_SEQ_NO BIGINT(12) NULL COMMENT 'Collection Point Sequence no.',
  RO_COLL_TYPE BIGINT(12) NULL COMMENT 'Collection Type',
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (ROD_ID))
COMMENT = 'Root Detail';