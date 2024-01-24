--liquibase formatted sql
--changeset jinea:V20171229180630__CR_TB_WMS_SCHEME_MAST_HIST.sql
CREATE TABLE TB_WMS_SCHEME_MAST_HIST (
  SCH_ID_H bigint(12) DEFAULT NULL,
  SCH_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  SCH_CODE varchar(10) DEFAULT NULL COMMENT 'Scheme Code',
  DP_DEPTID bigint(12) DEFAULT NULL COMMENT 'Department(fk with TB_DEPARTMENT)',
  SCH_NAME_ENG varchar(250) DEFAULT NULL COMMENT 'Scheme Name in English',
  SCH_NAME_REG varchar(250) DEFAULT NULL COMMENT 'Scheme Name in Regional',
  SCH_DESCRIPTION varchar(1000) DEFAULT NULL COMMENT 'Scheme Description',
  SCH_START_DATE date DEFAULT NULL COMMENT 'Scheme Start Date',
  SCH_END_DATE date DEFAULT NULL COMMENT 'Scheme End Date',
  SCH_FUND bigint(12) DEFAULT NULL COMMENT 'Scheme fund',
  SCH_PROJECTED_REVENU decimal(15,2) DEFAULT NULL COMMENT 'Scheme Projected Revenu',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  H_STATUS char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Scheme Master';
