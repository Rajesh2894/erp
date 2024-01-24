--liquibase formatted sql
--changeset Anil:V20200910175855__AL_tb_wms_milestone_mast_10092020.sql
ALTER TABLE tb_wms_milestone_mast CHANGE COLUMN milestone_nm milestone_id BIGINT(12) NULL;

