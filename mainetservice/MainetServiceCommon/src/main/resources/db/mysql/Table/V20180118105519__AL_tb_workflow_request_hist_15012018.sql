--liquibase formatted sql
--changeset priya:V20180118105519__AL_tb_workflow_request_hist_15012018.sql
drop table if exists tb_workflow_request_hist;

--liquibase formatted sql
--changeset priya:V20180118105519__AL_tb_workflow_request_hist_150120181.sql
CREATE TABLE tb_workflow_request_hist (
  WORKFLOW_REQ_ID_H bigint(12) DEFAULT NULL,
  WORKFLOW_REQ_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key of TB_WORKFLOW_REQUEST',
  DATE_OF_REQUEST datetime DEFAULT NULL COMMENT 'Request Date',
  PROCESS_SESSIONID bigint(10) DEFAULT NULL COMMENT 'Process Session Id',
  APM_APPLICATION_ID bigint(16) DEFAULT NULL COMMENT 'Application Id',
  STATUS varchar(30) DEFAULT NULL COMMENT 'Status',
  EMPL_TYPE bigint(12) DEFAULT NULL COMMENT 'Employee Type',
  EMPID bigint(19) DEFAULT NULL COMMENT 'Employee Id',
  WORFLOW_TYPE_ID bigint(12) DEFAULT NULL COMMENT 'WorkflowType id from workflow type Master',
  ORGID bigint(12) NOT NULL COMMENT 'Organisation Id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'Last modification date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'User id who update the data',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which data is going to update',
  H_STATUS varchar(1) DEFAULT NULL COMMENT 'History Status',
  APPLICATION_SLA_DURATION bigint(15) NOT NULL COMMENT 'Application Sla Duration'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
