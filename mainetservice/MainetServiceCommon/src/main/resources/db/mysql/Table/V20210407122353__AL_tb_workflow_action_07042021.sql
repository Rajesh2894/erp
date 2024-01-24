--liquibase formatted sql
--changeset Kanchan:V20210407122353__AL_tb_workflow_action_07042021.sql
ALTER TABLE tb_workflow_action  MODIFY COLUMN COMMENTS VARCHAR(3000);
