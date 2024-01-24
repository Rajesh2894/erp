--liquibase formatted sql
--changeset nilima:V20180620174148__AL_tb_wms_workestimate_mas_hist_200620181.sql
ALTER TABLE tb_wms_workestimate_mas_hist
CHANGE COLUMN WORKD_File_Name WORKE_File_Name VARCHAR(500) NULL DEFAULT NULL COMMENT 'when measuremnet upoaded' ;

