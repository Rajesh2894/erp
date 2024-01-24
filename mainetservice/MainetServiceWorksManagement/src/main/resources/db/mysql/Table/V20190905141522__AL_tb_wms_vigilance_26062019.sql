--liquibase formatted sql
--changeset Anil:V20190905141522__AL_tb_wms_vigilance_26062019.sql
ALTER TABLE tb_wms_vigilance 
ADD COLUMN PROJ_ID BIGINT(12) NULL AFTER VI_ID,
ADD COLUMN WORK_ID BIGINT(12) NULL AFTER PROJ_ID;
