--liquibase formatted sql
--changeset Anil:V20191022172140__AL_TB_WORKFLOW_REQUEST_19102019.sql
ALTER TABLE TB_WORKFLOW_REQUEST ADD COLUMN DEPLOYMENT_ID BIGINT(12) NULL AFTER UPDATED_DATE;


