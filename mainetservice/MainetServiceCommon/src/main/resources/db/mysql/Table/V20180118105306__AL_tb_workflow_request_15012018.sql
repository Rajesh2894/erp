--liquibase formatted sql
--changeset priya:V20180118105306__AL_tb_workflow_request_15012018.sql
ALTER TABLE tb_workflow_request 
ADD COLUMN APPLICATION_SLA_DURATION BIGINT(15) NOT NULL COMMENT 'Application Sla Duration' AFTER LAST_DECISION;
--liquibase formatted sql
--changeset priya:V20180118105306__AL_tb_workflow_request_150120181.sql
ALTER TABLE tb_workflow_action 
ADD COLUMN TASK_SLA_DURATION BIGINT(15) NOT NULL COMMENT 'Task Sla Duration' AFTER UPDATED_DATE;