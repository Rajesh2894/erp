--liquibase formatted sql
--changeset nilima:V20171031182714__al_tb_workflow_det_13102017.sql
ALTER TABLE tb_workflow_det
ADD COLUMN WFD_EMPROLE VARCHAR(200) NULL COMMENT 'Employee/Role Detail' AFTER `WFD_APPR_COUNT`;

