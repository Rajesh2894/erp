--liquibase formatted sql
--changeset nilima:V20180511160327__CR_TB_SW_VEHICLE_MAINTAINANCE.sql
DROP TABLE IF EXISTS TB_SW_VEHICLE_MAINTENANCE;
--changeset nilima:V20180511160327__CR_TB_SW_VEHICLE_MAINTAINANCE1.sql
CREATE TABLE TB_SW_VEHICLE_MAINTENANCE (
  VE_MEID bigint(12) NOT NULL COMMENT 'Primary Key',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  VE_MAINDAY bigint(3) DEFAULT NULL COMMENT 'Maintenance After',
  VE_MAINUNIT bigint(12) DEFAULT NULL COMMENT 'Maintenance Unit',
  VE_DOWNTIME bigint(3) DEFAULT NULL COMMENT 'Estimated Downtime',
  VE_DOWNTIMEUNIT bigint(12) DEFAULT NULL COMMENT 'Maintenance Unit',
  VE_MEACTIVE char(1) DEFAULT NULL COMMENT 'Active-''Y'',Inactive->''N''',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VE_MEID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Maintainacne Master';