--liquibase formatted sql
--changeset nilima:V20190328171808__AL_tb_wms_workdefination_hist_27032019.sql
drop table if exists tb_wms_workdefination_hist;

--liquibase formatted sql
--changeset nilima:V20190328171808__AL_tb_wms_workdefination_hist_270320191.sql
CREATE TABLE tb_wms_workdefination_hist (
  WORK_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  WORK_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  WORK_NAME varchar(500) DEFAULT NULL COMMENT 'Work Name',
  PROJ_ID bigint(12) DEFAULT NULL COMMENT 'Project Code (Foregin Key TB_WMS_PROJECT_MAST)',
  WORK_START_DATE date DEFAULT NULL COMMENT 'Work Start date ',
  WORK_END_DATE date DEFAULT NULL COMMENT 'Work end date ',
  WORK_TYPE bigint(12) DEFAULT NULL COMMENT 'Work Type(Capital/Maintenance)(prefix)',
  DP_DEPTID bigint(12) DEFAULT NULL COMMENT 'Execution Department',
  WORK_PROJECT_PHASE bigint(12) DEFAULT NULL COMMENT 'Project Phase (preifix)',
  LOC_ID_ST bigint(12) DEFAULT NULL COMMENT 'Start Location Id',
  LOC_ID_EN bigint(12) DEFAULT NULL COMMENT 'End Location Id',
  WORK_ESTAMT decimal(15,2) DEFAULT NULL,
  WORK_CODE varchar(50) DEFAULT NULL,
  WORK_COMPLETION_NO varchar(50) DEFAULT NULL,
  WORK_COMPLETION_DT date DEFAULT NULL,
  WORK_STATUS char(3) DEFAULT NULL COMMENT '(D->Draft,P->Pending,A->Approved,AA->Administrator Approval,TA->Technical Approval)',
  H_STATUS char(1) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WORK_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Work Defination History';
