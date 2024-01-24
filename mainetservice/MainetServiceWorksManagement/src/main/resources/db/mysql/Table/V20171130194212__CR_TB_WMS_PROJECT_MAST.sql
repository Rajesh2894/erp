--liquibase formatted sql
--changeset nilima:V20171130194212__CR_TB_WMS_PROJECT_MAST.sql
CREATE TABLE TB_WMS_PROJECT_MAST (
  PROJ_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  PROJ_TYPE bigint(12) NOT NULL COMMENT 'Project Type',
  PROJ_CODE varchar(10) NOT NULL COMMENT 'Project Code',
  DP_DEPTID bigint(12) NOT NULL COMMENT 'Department(fk with TB_DEPARTMENT)',
  PROJ_NAME_ENG varchar(250) NOT NULL COMMENT 'Project Name in English',
  PROJ_NAME_REG varchar(250) DEFAULT NULL COMMENT 'Project Name in Regional',
  PROJ_DESCRIPTION varchar(1000) NOT NULL COMMENT 'Project Description',
  PROJ_START_DATE date NOT NULL COMMENT 'Project Start Date',
  PROJ_END_DATE date DEFAULT NULL COMMENT 'Project End Date',
  SCH_ID bigint(12) DEFAULT NULL COMMENT 'Scheme ID',
  PROJ_ESTIMATE_COST decimal(15,2) DEFAULT NULL COMMENT 'Project Estimated Cost',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (PROJ_ID)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Project Master';

