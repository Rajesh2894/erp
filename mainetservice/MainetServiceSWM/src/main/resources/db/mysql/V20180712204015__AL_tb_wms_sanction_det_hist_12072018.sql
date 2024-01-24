--liquibase formatted sql
--changeset nilima:V20180712204015__AL_tb_wms_sanction_det_hist_12072018.sql
ALTER TABLE tb_wms_sanction_det_hist 
CHANGE COLUMN SM_SERVICE_ID DP_DEPTID BIGINT(12) NULL DEFAULT NULL ;
