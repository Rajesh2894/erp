--liquibase formatted sql
--changeset nilima:V20180829201106__CR_TB_WMS_LEADLIFT_MAST_HIST_29082018.sql
CREATE TABLE TB_WMS_LEADLIFT_MAST_HIST (
  LELI_ID_H bigint(12) NOT NULL,
  LELI_ID bigint(12) DEFAULT NULL COMMENT 'primary key',
  LELI_FROM decimal(12,2) DEFAULT NULL COMMENT 'Lead From',
  LELI_TO decimal(12,2) DEFAULT NULL COMMENT 'Lead From',
  LELI_UNIT bigint(12) DEFAULT NULL COMMENT 'Unit',
  LELI_RATE decimal(12,2) DEFAULT NULL COMMENT 'Rate',
  LELI_FLAG char(1) DEFAULT NULL COMMENT 'Lead Lift flag(L-> Lead,Lift->F)',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  SOR_ID bigint(12) DEFAULT NULL COMMENT 'Schedule Rate Master ID',
  LELI_ACTIVE char(1) DEFAULT NULL COMMENT 'Active/Inactive',
  H_STATUS char(1) DEFAULT NULL,
  PRIMARY KEY (LELI_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;