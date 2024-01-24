--liquibase formatted sql
--changeset priya:V20180221160412__CR_TB_WMS_WORKDEF_ASSET_INFO.sql
CREATE TABLE TB_WMS_WORKDEF_ASSET_INFO (
  WORK_ASSETID bigint(12) NOT NULL COMMENT 'Primary Key',
  WORK_ID bigint(12) DEFAULT NULL COMMENT 'Foregin Key TB_WMS_WORKDEF_ASSET_INFO',
  ASSET_ID bigint(12) DEFAULT NULL COMMENT 'Asset ID',
  ASSET_CODE varchar(50) DEFAULT NULL COMMENT 'Asset Code',
  ASSET_NAME varchar(50) DEFAULT NULL COMMENT 'Asset Name',
  ASSET_CATEGORY varchar(50) DEFAULT NULL COMMENT 'Asset Category',
  ASSET_DEPARTMENT varchar(50) DEFAULT NULL COMMENT 'Asset Department',
  ASSET_LOCATION varchar(50) DEFAULT NULL COMMENT 'Asset Location',
  ASSET_STATUS varchar(50) DEFAULT NULL COMMENT 'Asset Status',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WORK_ASSETID),
  KEY FK_ASS_WORKID_idx (WORK_ID),
  CONSTRAINT FK_ASS_WORKID FOREIGN KEY (WORK_ID) REFERENCES tb_wms_workdefination (WORK_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='WorkDefination Asset Info';