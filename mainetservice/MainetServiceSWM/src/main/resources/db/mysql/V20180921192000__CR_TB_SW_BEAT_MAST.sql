--liquibase formatted sql
--changeset nilima:V20180921192000__CR_TB_SW_BEAT_MAST.sql
CREATE TABLE TB_SW_BEAT_MAST (
  BEAT_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  BEAT_NO varchar(50) NOT NULL COMMENT 'BEAT Number',
  BEAT_NAME varchar(100) NOT NULL COMMENT 'BEAT Name',
  BEAT_NAME_REG varchar(45) DEFAULT NULL COMMENT 'BEAT Name Regional',
  BEAT_START_POINT bigint(12) DEFAULT NULL COMMENT 'Starting Point',
  BEAT_END_POINT bigint(12) DEFAULT NULL COMMENT 'End Point',
  BEAT_DISTANCE decimal(12,2) DEFAULT NULL COMMENT 'Total BEAT Distance',
  BEAT_DISTANCE_UNIT bigint(12) DEFAULT NULL COMMENT 'Total BEAT Distance Unit',
  BEAT_AREATYPE bigint(12) DEFAULT NULL COMMENT 'Area Type',
  BEAT_VE_TYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  DE_ID bigint(12) NOT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST',
  BEAT_DIST_DES decimal(12,2) DEFAULT NULL COMMENT 'Distance from Disposal Site',
  BEAT_COLL_UNITCNT bigint(12) NOT NULL COMMENT 'Collection Unit Count',
  BEAT_ASSUME_WETQTY bigint(12) NOT NULL COMMENT 'Assume Wet Quantity',
  BEAT_ASSUME_DRYQTY bigint(12) NOT NULL COMMENT 'Assume Dry Quantity',
  BEAT_ASSUME_OTHERQTY bigint(12) NOT NULL COMMENT 'Assume Other Quantity',
  BEAT_ACTIVE char(1) NOT NULL DEFAULT 'N' COMMENT '(Y->Active, N->Active)',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address fBEATm where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address fBEATm where user has updated the record',
  PRIMARY KEY (BEAT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;