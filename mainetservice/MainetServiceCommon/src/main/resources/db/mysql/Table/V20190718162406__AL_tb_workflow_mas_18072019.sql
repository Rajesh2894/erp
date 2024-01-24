--liquibase formatted sql
--changeset Anil:V20190718162406__AL_tb_workflow_mas_18072019.sql
ALTER TABLE tb_workflow_mas 
ADD COLUMN WF_SCH_NAME BIGINT(12) NULL AFTER WF_TOAMT;
