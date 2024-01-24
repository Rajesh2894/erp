--liquibase formatted sql
--changeset priya:V20180303122228__CR_TB_WMS_WORKDEFINATION_HIST.sql
DROP TABLE IF EXISTS TB_WMS_WORKDEFINATION_HIST;
--liquibase formatted sql
--changeset priya:V20180303122228__CR_TB_WMS_WORKDEFINATION_HIST1.sql
CREATE TABLE TB_WMS_WORKDEFINATION_HIST (
  WORK_ID_H bigint(12) COMMENT 'Primary Key',
  WORK_ID bigint(12)  COMMENT 'Primary Key',
  WORK_NAME varchar(500)  COMMENT 'Work Name',
  PROJ_ID bigint(12)  COMMENT 'Project Code (Foregin Key TB_WMS_PROJECT_MAST)',
  WORK_START_DATE date  COMMENT 'Work Start date ',
  WORK_END_DATE date  COMMENT 'Work end date ',
  WORK_TYPE bigint(12)  COMMENT 'Work Type(Capital/Maintenance)(prefix)',
  DP_DEPTID bigint(12)  COMMENT 'Execution Department',
  WORK_PROJECT_PHASE bigint(12)  COMMENT 'Project Phase (preifix)',
  LOC_ID_ST bigint(12)  COMMENT 'Start Location Id',
  LOC_ID_EN bigint(12)  COMMENT 'End Location Id',
  WORK_CODE varchar(50) ,
  WORK_STATUS char(3) DEFAULT NULL COMMENT '(D->Draft,P->Pending,A->Approved,AA->Administrator Approval,TA->Technical Approval)',
  H_STATUS char(1) COMMENT 'Record Status',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WORK_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Work Defination History';