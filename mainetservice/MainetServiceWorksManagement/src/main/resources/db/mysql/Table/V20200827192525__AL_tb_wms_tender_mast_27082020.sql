--liquibase formatted sql
--changeset Anil:V20200827192525__AL_tb_wms_tender_mast_27082020.sql
ALTER TABLE tb_wms_tender_mast CHANGE COLUMN TND_PUBLISH_DATE TND_PUBLISH_DATE DATETIME NULL DEFAULT NULL COMMENT 'Tender Publish Date' ;
