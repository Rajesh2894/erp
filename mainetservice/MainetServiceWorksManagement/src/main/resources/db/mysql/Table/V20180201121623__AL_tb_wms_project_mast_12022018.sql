--liquibase formatted sql
--changeset priya:V20180201121623__AL_tb_wms_project_mast_1202018.sql
ALTER TABLE tb_wms_project_mast
ADD COLUMN PROJ_RISK BIGINT(12) NULL COMMENT 'Project Risk' AFTER PROJ_END_DATE,
ADD COLUMN PROJ_COMPLEXITY BIGINT(12) NULL COMMENT 'Project Complexity' AFTER PROJ_RISK;