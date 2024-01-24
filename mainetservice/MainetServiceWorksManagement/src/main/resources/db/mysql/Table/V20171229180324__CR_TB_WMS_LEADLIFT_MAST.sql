--liquibase formatted sql
--changeset jinea:V20171229180324__CR_TB_WMS_LEADLIFT_MAST.sql
CREATE TABLE TB_WMS_LEADLIFT_MAST (
  LELI_ID bigint(12) NOT NULL COMMENT 'primary key',
  WC_ID bigint(12) DEFAULT NULL COMMENT 'Work Category Id',
  LELI_FROM decimal(12,2) NOT NULL COMMENT 'Lead From',
  LELI_TO decimal(12,2) NOT NULL COMMENT 'Lead From',
  LELI_UNIT bigint(12) NOT NULL COMMENT 'Unit',
  LELI_RATE decimal(12,2) NOT NULL COMMENT 'Rate',
  LELI_FLAG char(1) NOT NULL COMMENT 'Lead Lift flag(L-> Lead,Lift->F)',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  SOR_ID bigint(12) NOT NULL COMMENT 'Schedule Rate Master ID',
  LELI_ACTIVE char(1) DEFAULT NULL COMMENT 'Active/Inactive',
  PRIMARY KEY (LELI_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
