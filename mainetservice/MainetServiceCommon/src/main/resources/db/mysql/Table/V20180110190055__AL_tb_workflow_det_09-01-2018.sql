--liquibase formatted sql
--changeset jinea:V20180110190055__AL_tb_workflow_det_09-01-2018.sql
ALTER TABLE tb_workflow_det 
ADD COLUMN SLA_CLA BIGINT(15) NULL DEFAULT NULL COMMENT 'Conversion Of SLA into Milisecond'  AFTER WFD_EMPROLE;

--liquibase formatted sql
--changeset jinea:V20180110190055__AL_tb_workflow_det_09-01-2018_1.sql
ALTER TABLE tb_workflow_det 
CHANGE COLUMN SLA_CLA SLA_CAL BIGINT(15) NULL DEFAULT NULL COMMENT 'Conversion Of SLA into Milisecond'  ;