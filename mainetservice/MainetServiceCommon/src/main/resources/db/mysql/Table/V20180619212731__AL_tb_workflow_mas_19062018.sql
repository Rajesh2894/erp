--liquibase formatted sql
--changeset nilima:V20180619212731__AL_tb_workflow_mas_19062018.sql
ALTER TABLE tb_workflow_mas 
ADD COLUMN WF_FROMAMT DECIMAL(20,2) NULL COMMENT 'Workflow for Amount' AFTER LG_IP_MAC_UPD,
ADD COLUMN WF_TOAMT DECIMAL(20,2) NULL COMMENT 'Workflow tor Amount' AFTER WF_FROMAMT;
