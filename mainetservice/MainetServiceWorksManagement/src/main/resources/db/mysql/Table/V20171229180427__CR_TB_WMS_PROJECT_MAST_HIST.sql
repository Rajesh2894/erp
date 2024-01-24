--liquibase formatted sql
--changeset jinea:V20171229180427__CR_TB_WMS_PROJECT_MAST_HIST.sql
CREATE TABLE TB_WMS_SCHEME_DET_HIST (
  SCHD_ID_H bigint(12) DEFAULT NULL,
  SCHD_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  SCH_ID bigint(12) DEFAULT NULL COMMENT 'Foregin  Key(TB_WMS_SCHEME_MAST)',
  SCHD_SPONSORED_BY varchar(250) DEFAULT NULL COMMENT 'Scheme Sponsoror BY',
  SCHD_SHARING_PER decimal(3,2) DEFAULT NULL COMMENT 'Scheme Sharing Percentage',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  H_STATUS char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


