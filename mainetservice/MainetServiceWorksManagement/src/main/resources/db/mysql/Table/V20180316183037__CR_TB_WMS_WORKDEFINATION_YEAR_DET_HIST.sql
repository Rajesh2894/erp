--liquibase formatted sql
--changeset shamik:V20180316183037__CR_TB_WMS_WORKDEFINATION_YEAR_DET_HIST1.sql
DROP table IF exists TB_WMS_WORKDEFINATION_YEAR_DET_HIST;
--liquibase formatted sql
--changeset shamik:V20180316183037__CR_TB_WMS_WORKDEFINATION_YEAR_DET_HIST2.sql
CREATE TABLE TB_WMS_WORKDEFINATION_YEAR_DET_HIST(
  YE_ID_H bigint(12)  COMMENT 'Primary Key',
  YE_ID   bigint(12)  COMMENT 'Primary Key',
  WORK_ID bigint(12)  COMMENT 'foregin key TB_WMS_WORKESTIMATE_MAST',
  YE_FINANCE_CODE_DESC varchar(45)  COMMENT 'Code description if account is in setting mode',
  SAC_HEAD_ID bigint(12)  COMMENT 'BugetCode',
  FA_YEARID bigint(12)  COMMENT 'Financiale Year Id',
  YE_PERCENT_WORK decimal(6,2)  COMMENT 'Percentage',
  YE_DOC_REFERENCENO varchar(50)  COMMENT 'Document Reference Number',
  YE_BUGEDED_AMOUNT decimal(15,2)  COMMENT 'Bugeted Amount',
  YE_ACTIVE char(1)  COMMENT 'Active/Inactive',
  H_STATUS char(1)  COMMENT 'Record Status',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (YE_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Work defination year detail History';
