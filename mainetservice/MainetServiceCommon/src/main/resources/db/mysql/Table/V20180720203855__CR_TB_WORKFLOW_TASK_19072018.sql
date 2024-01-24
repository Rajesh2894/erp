--liquibase formatted sql
--changeset nilima:V20180720203855__CR_TB_WORKFLOW_TASK_19072018.sql
CREATE TABLE TB_WORKFLOW_TASK (
  WFTASK_ID bigint(12) NOT NULL,
  TASK_ID bigint(12) DEFAULT NULL,
  TASK_NAME varchar(1000) DEFAULT NULL,
  TASK_STATUS varchar(30) DEFAULT NULL,
  APM_APPLICATION_ID bigint(12) DEFAULT NULL,
  REFERENCE_ID varchar(50) DEFAULT NULL,
  WF_ID bigint(12) DEFAULT NULL,
  DP_DEPTID bigint(12) DEFAULT NULL,
  DP_DEPTDESC varchar(800) DEFAULT NULL,
  DP_NAME_MAR varchar(800) DEFAULT NULL,
  SM_SERVICE_ID bigint(12) DEFAULT NULL,
  SM_SERVICE_NAME varchar(200) DEFAULT NULL,
  SM_SERVICE_NAME_MAR varchar(400) DEFAULT NULL,
  EVENT_ID bigint(12) DEFAULT NULL,
  SMFNAME varchar(2000) DEFAULT NULL,
  SMFNAME_MAR varchar(2000) DEFAULT NULL,
  SMFACTION varchar(400) DEFAULT NULL,
  WFTASK_ACTORID varchar(50) DEFAULT NULL,
  WFTASK_ROLEID varchar(50) DEFAULT NULL,
  WFTASK_ESCALLEVEL bigint(5) DEFAULT NULL,
  TASK_SLA_DURATION bigint(15) DEFAULT NULL,
  WFTASK_CCHEKLEVEL bigint(5) DEFAULT NULL,
  WFTASK_CCHEKGROUP bigint(5) DEFAULT NULL,
  WFTASK_ASSIGDATE datetime DEFAULT NULL,
  WFTASK_COMPDATE datetime DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  PRIMARY KEY (WFTASK_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  
