--liquibase formatted sql
--changeset priya:V20180303122056__AL_TB_WMS_WORKDEF_ASSET_INFO_HIST.sql
DROP TABLE IF EXISTS TB_WMS_WORKDEF_ASSET_INFO_HIST;
--liquibase formatted sql
--changeset priya:V20180303122056__AL_TB_WMS_WORKDEF_ASSET_INFO_HIST1.sql
CREATE TABLE TB_WMS_WORKDEF_ASSET_INFO_HIST(
  WORK_ASSETID_H bigint(12) COMMENT 'Primary Key',
  WORK_ASSETID bigint(12) COMMENT 'Primary Key',
  WORK_ID bigint(12)  COMMENT 'Foregin Key TB_WMS_WORKDEF_ASSET_INFO',
  ASSET_ID bigint(12)  COMMENT 'Asset ID',
  ASSET_CODE varchar(50)  COMMENT 'Asset Code',
  ASSET_NAME varchar(50)  COMMENT 'Asset Name',
  ASSET_CATEGORY varchar(50)  COMMENT 'Asset Category',
  ASSET_DEPARTMENT varchar(50)  COMMENT 'Asset Department',
  ASSET_LOCATION varchar(50)  COMMENT 'Asset Location',
  ASSET_STATUS varchar(50)  COMMENT 'Asset Status',
  H_STATUS CHAR(1) COMMENT 'History Status',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  ASSET_REC_STATUS char(1)  COMMENT 'Active->Y,InActive->N',
  PRIMARY KEY (WORK_ASSETID_H)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='WorkDefination Asset Info History';