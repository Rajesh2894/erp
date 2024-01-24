--liquibase formatted sql
--changeset Anil:V20200527192453__AL_tb_wms_tender_work_27052020.sql
ALTER TABLE tb_wms_tender_work ADD COLUMN TND_TYPE_PERCENT BIGINT(12) NULL;

