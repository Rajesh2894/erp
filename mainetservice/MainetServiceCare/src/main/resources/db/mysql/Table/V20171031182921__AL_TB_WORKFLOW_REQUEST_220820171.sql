--liquibase formatted sql
--changeset nilima:V20171031182921__AL_TB_WORKFLOW_REQUEST_220820171.sql
drop table tb_workflow_request;

--liquibase formatted sql
--changeset nilima:V20171031182921__AL_TB_WORKFLOW_REQUEST_220820172.sql
CREATE TABLE tb_workflow_request (
  WORKFLOW_REQ_ID bigint(19) NOT NULL COMMENT 'primary key',
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  DATE_OF_REQUEST datetime NOT NULL COMMENT 'Request Date',
  PROCESS_SESSIONID bigint(10) DEFAULT NULL COMMENT 'Process Session Id',
  APM_APPLICATION_ID bigint(16) NOT NULL COMMENT 'Application Id',
  STATUS varchar(30) NOT NULL COMMENT 'Status',
  EMPL_TYPE bigint(12) DEFAULT NULL COMMENT 'Employee Type',
  EMPID bigint(19) NOT NULL COMMENT 'Employee Id',
  WORFLOW_TYPE_ID bigint(12) NOT NULL COMMENT 'WorkflowType id from workflow type Master',
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  PRIMARY KEY (WORKFLOW_REQ_ID),
  KEY FK_WORK_EMP_idx (EMPID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


