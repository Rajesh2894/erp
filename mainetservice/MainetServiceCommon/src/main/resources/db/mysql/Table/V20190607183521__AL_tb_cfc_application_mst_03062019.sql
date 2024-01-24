--liquibase formatted sql
--changeset nilima:V20190607183521__AL_tb_cfc_application_mst_03062019.sql
ALTER TABLE tb_cfc_application_mst
ADD COLUMN APM_MODE CHAR(2) NULL COMMENT 'Application Mode' AFTER `APM_BPL_ISSU_AUT`;
