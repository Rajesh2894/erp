--liquibase formatted sql
--changeset nilima:V20180609165402__CR_TB_SW_VEHICLE_SCHEDDET_HIST_06062018.sql
CREATE TABLE TB_SW_VEHICLE_SCHEDDET_HIST (
  VESD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VESD_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VES_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_VEHICLE_SCHEDULING',
  RO_ID bigint(12) DEFAULT NULL COMMENT 'FK tb_sw_route_mast',
  VES_STARTIME datetime DEFAULT NULL,
  VES_ENDTIME datetime DEFAULT NULL,
  VES_WEEKDAY int(5) DEFAULT NULL COMMENT 'Week Day',
  VES_MONTH int(5) DEFAULT NULL COMMENT 'Week Month',
  VES_COLL_TYPE bigint(12) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VESD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Schedule Detail History';

