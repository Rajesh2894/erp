--liquibase formatted sql
--changeset nilima:V20180625162050__AL_tb_wms_project_mast_hist_25062018.sql
drop table if exists tb_wms_project_mast_hist;

--liquibase formatted sql
--changeset nilima:V20180625162050__AL_tb_wms_project_mast_hist_250620181.sql
CREATE TABLE tb_wms_project_mast_hist (
  PROJ_ID_H bigint(12) DEFAULT NULL,
  PROJ_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  PROJ_TYPE bigint(12) DEFAULT NULL COMMENT 'Project Type',
  PROJ_CODE varchar(10) DEFAULT NULL COMMENT 'Project Code',
  DP_DEPTID bigint(12) DEFAULT NULL COMMENT 'Department(fk with TB_DEPARTMENT)',
  PROJ_NAME_ENG varchar(250) DEFAULT NULL COMMENT 'Project Name in English',
  PROJ_NAME_REG varchar(250) DEFAULT NULL COMMENT 'Project Name in Regional',
  PROJ_DESCRIPTION varchar(1000) DEFAULT NULL COMMENT 'Project Description',
  PROJ_START_DATE date DEFAULT NULL COMMENT 'Project Start Date',
  PROJ_END_DATE date DEFAULT NULL COMMENT 'Project End Date',
  PROJ_RISK bigint(12) DEFAULT NULL,
  PROJ_COMPLEXITY bigint(12) DEFAULT NULL,
  SCH_ID bigint(12) DEFAULT NULL COMMENT 'Scheme ID',
  PROJ_ESTIMATE_COST decimal(15,2) DEFAULT NULL COMMENT 'Project Estimated Cost',
  PROJ_ACTUAL_COST decimal(15,2) DEFAULT NULL,
  PROJ_ACTIVE char(1) DEFAULT NULL,
  RSO_NO varchar(45) DEFAULT NULL,
  RSO_DATE date DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  H_STATUS char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Project Master History';