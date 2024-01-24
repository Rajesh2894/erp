--liquibase formatted sql
--changeset nilima:V20180720203715__CR_tb_wms_workeorder_19072018.sql
ALTER TABLE tb_wms_workeorder 
ADD COLUMN WORKOR_STATUS CHAR(1) NULL COMMENT 'WorkOrder Status' AFTER WORKOR_DEFECTLIABILITYPER;
