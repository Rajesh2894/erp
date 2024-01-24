--liquibase formatted sql
--changeset Anil:V20200903141208__AL_tb_wms_milestone_mast_03092020.sql
ALTER TABLE tb_wms_milestone_mast ADD COLUMN milestone_nm VARCHAR(50) NULL;

