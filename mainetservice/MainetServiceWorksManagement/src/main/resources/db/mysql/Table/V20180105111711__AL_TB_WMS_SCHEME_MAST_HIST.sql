--liquibase formatted sql
--changeset jinea:V20180105111711__AL_TB_WMS_SCHEME_MAST_HIST.sql
drop table if exists tb_wms_scheme_mast_hist;
--liquibase formatted sql
--changeset jinea:V20180105111711__AL_TB_WMS_SCHEME_MAST_HIST1.sql
CREATE TABLE tb_wms_scheme_mast_hist (
  SCH_ID_H bigint(12) DEFAULT NULL,
  SCH_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  SCH_CODE varchar(10) DEFAULT NULL COMMENT 'Scheme Code',
  SCH_SCODID1 bigint(12) DEFAULT NULL,
  DP_DEPTID bigint(12) DEFAULT NULL COMMENT 'Department(fk with TB_DEPARTMENT)',
  SCH_NAME_ENG varchar(250) DEFAULT NULL COMMENT 'Scheme Name in English',
  SCH_NAME_REG varchar(250) DEFAULT NULL COMMENT 'Scheme Name in Regional',
  SCH_DESCRIPTION varchar(1000) DEFAULT NULL COMMENT 'Scheme Description',
  SCH_START_DATE date DEFAULT NULL COMMENT 'Scheme Start Date',
  SCH_END_DATE date DEFAULT NULL COMMENT 'Scheme End Date',
  SCH_FUND bigint(12) DEFAULT NULL COMMENT 'Scheme fund',
  SCH_FUNDNAME varchar(50) DEFAULT NULL,
  SCH_PROJECTED_REVENU decimal(15,2) DEFAULT NULL COMMENT 'Scheme Projected Revenu',
  SCH_ACTIVE char(1) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  SCH_SCODID2 bigint(12) DEFAULT NULL COMMENT 'SchemeCodeid'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Scheme Master';
