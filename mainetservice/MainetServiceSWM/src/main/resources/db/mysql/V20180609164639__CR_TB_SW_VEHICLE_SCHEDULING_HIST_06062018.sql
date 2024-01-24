--liquibase formatted sql
--changeset nilima:V20180609164639__CR_TB_SW_VEHICLE_SCHEDULING_HIST_06062018.sql
CREATE TABLE TB_SW_VEHICLE_SCHEDULING_HIST (
  VES_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VES_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'vehicle type (''VHT'')',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vender Id',
  VES_FROMDT date DEFAULT NULL COMMENT 'Schedule From Date',
  VES_TODT date DEFAULT NULL COMMENT 'Schedule To Date',
  VES_REOCC char(1) DEFAULT NULL COMMENT 'Reoccurance(D->Daily,W->Weekly,M->)',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VES_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Scheduling Mast Hist';