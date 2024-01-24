--liquibase formatted sql
--changeset nilima:V20190617174118__AL_tb_ml_item_detail_03062019.sql
ALTER TABLE tb_ml_item_detail
ADD COLUMN TRI_STATUS CHAR(2) NULL COMMENT 'Item Active/Inactive' AFTER `LG_IP_MAC_UPD`;
