--liquibase formatted sql
--changeset nilima:V20180511155550__CR_TB_SW_ROOT_MAST.sql
DROP TABLE IF EXISTS TB_SW_ROOT_MAST;
--changeset nilima:V20180511155550__CR_TB_SW_ROOT_MAST1.sql
CREATE TABLE TB_SW_ROOT_MAST (
  RO_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  RO_NO VARCHAR(50) NULL COMMENT 'Route Number',
  RO_NAME VARCHAR(100) NULL COMMENT 'Route Name',
  RO_NAME_REG VARCHAR(45) NULL COMMENT 'Route Name Regional',
  COD_WARD1 BIGINT(12) NULL COMMENT 'Ward',
  COD_WARD2 BIGINT(12) NULL COMMENT 'Zone',
  COD_WARD3 BIGINT(12) NULL COMMENT 'Block',
  COD_WARD4 BIGINT(12) NULL COMMENT 'route',
  COD_WARD5 BIGINT(12) NULL,
  RO_START_POINT BIGINT(12) NULL COMMENT 'Starting Point',
  RO_END_POINT BIGINT(12) NULL COMMENT 'End Point',
  RO_DISTANCE DECIMAL(12,2) NULL COMMENT 'Total Route Distance',
  RO_DISTANCE_UNIT BIGINT(12) NULL COMMENT 'Total Route Distance Unit',
  RO_VE_TYPE BIGINT(12) NULL COMMENT 'Vechicle Type',
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL,
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (RO_ID));
