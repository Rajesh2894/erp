--liquibase formatted sql
--changeset nilima:V20170818151856__AL_TB_WORKFLOW_REQUEST_17082017.sql
ALTER TABLE TB_WORKFLOW_REQUEST
ADD COLUMN WORFLOW_TYPE_ID BIGINT(12) NULL COMMENT 'WorkflowType id from workflow type Master' AFTER DEPT_ID;
