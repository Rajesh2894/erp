--liquibase formatted sql
--changeset Anil:V20200128131639__AL_tb_wms_tender_mast_28012020.sql
ALTER TABLE tb_wms_tender_mast ADD COLUMN TND_REF_NO BIGINT(16) NULL AFTER LG_IP_MAC_UPD;
