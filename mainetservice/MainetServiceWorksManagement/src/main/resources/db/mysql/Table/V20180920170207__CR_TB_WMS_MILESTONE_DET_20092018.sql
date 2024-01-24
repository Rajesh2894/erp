--liquibase formatted sql
--changeset nilima:V20180920170207__CR_TB_WMS_MILESTONE_DET_20092018.sql
CREATE TABLE TB_WMS_MILESTONE_DET (
  MILED_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  MILE_ID bigint(12) DEFAULT NULL COMMENT 'Foregin Key TB_WMS_MILESTONE_MAST',
  MILED_PROGRESSDATE datetime DEFAULT NULL COMMENT 'Progress Date',
  MILED_PHYSC_PERCENT decimal(6,2) DEFAULT NULL COMMENT 'Physical Percentage',
  MILED_FINANC_PERCENT decimal(6,2) DEFAULT NULL COMMENT 'Financiale Percentage',
  MILED_ACTIVE char(1) DEFAULT NULL COMMENT 'organization id',
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MILED_ID),
  KEY FK_MILEID_idx (MILE_ID),
  CONSTRAINT FK_MILEID FOREIGN KEY (MILE_ID) REFERENCES tb_wms_milestone_mast (MILE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;