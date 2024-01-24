--liquibase formatted sql
--changeset nilima:V20190125191040__AL_TB_SW_BEAT_MAST_HIST_25012019.sql
drop table if exists TB_SW_BEAT_MAST_HIST
--liquibase formatted sql
--changeset nilima:V20190125191040__AL_TB_SW_BEAT_MAST_HIST_250120191.sql
CREATE TABLE TB_SW_BEAT_MAST_HIST (
  BEAT_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  BEAT_ID bigint(12)  COMMENT 'Primary Key',
  BEAT_NO varchar(50)  COMMENT 'BEAT Number',
  BEAT_NAME varchar(100)  COMMENT 'BEAT Name',
  BEAT_NAME_REG varchar(45) DEFAULT NULL COMMENT 'BEAT Name Regional',
  BEAT_START_POINT bigint(12)  COMMENT 'Starting Point',
  BEAT_END_POINT bigint(12)  COMMENT 'End Point',
  BEAT_DISTANCE decimal(12,2)  COMMENT 'Total BEAT Distance',
  BEAT_DISTANCE_UNIT bigint(12)  COMMENT 'Total BEAT Distance Unit',
  BEAT_AREATYPE bigint(12)  COMMENT 'Area Type',
  BEAT_VE_TYPE bigint(12)  COMMENT 'Vechicle Type',
  DE_ID bigint(12)  COMMENT 'FK TB_SW_DESPOSAL_MAST',
  BEAT_DIST_DES decimal(12,2)  COMMENT 'Distance from Disposal Site',
  BEAT_COLL_UNITCNT bigint(12)  COMMENT 'Collection Unit Count',
  BEAT_ASSUME_WETQTY bigint(12)  COMMENT 'Assume Wet Quantity',
  BEAT_ASSUME_DRYQTY bigint(12)  COMMENT 'Assume Dry Quantity',
  BEAT_ASSUME_OTHERQTY bigint(12)  COMMENT 'Assume Other Quantity',
  BEAT_ACTIVE char(1)  DEFAULT 'N' COMMENT '(Y->Active, N->Active)',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12) ,
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address fBEATm where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address fBEATm where user has updated the record',
  PRIMARY KEY (BEAT_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;