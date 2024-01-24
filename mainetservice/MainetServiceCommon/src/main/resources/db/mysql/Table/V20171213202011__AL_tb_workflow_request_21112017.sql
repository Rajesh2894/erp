--liquibase formatted sql
--changeset nilima:V20171213202011__AL_tb_workflow_request_21112017.sql
ALTER TABLE tb_workflow_request
ADD COLUMN PROCESS_NAME VARCHAR(50) NULL COMMENT '' AFTER CREATED_BY;

