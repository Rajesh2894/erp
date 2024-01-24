--liquibase formatted sql
--changeset nilima:V20180629200335__AL_tb_wms_sanction_det_29062018.sql
ALTER TABLE tb_wms_sanction_det
CHANGE COLUMN SM_SERVICE_ID DP_DEPTID BIGINT(12) NULL DEFAULT NULL COMMENT 'Department Id' ;