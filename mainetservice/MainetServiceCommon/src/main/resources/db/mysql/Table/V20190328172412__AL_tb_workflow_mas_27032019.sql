--liquibase formatted sql
--changeset nilima:V20190328172412__AL_tb_workflow_mas_27032019.sql
ALTER TABLE tb_workflow_mas
ADD COLUMN WF_CONDITION BIGINT(12) NULL AFTER COD_ID_OPER_LEVEL5;
