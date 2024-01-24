--liquibase formatted sql
--changeset Anil:V20191005162904__AL_tb_workflow_mas_05102019.sql
ALTER TABLE tb_workflow_mas ADD COLUMN COMP_RE CHAR(1) NULL AFTER WF_SCH_NAME;
