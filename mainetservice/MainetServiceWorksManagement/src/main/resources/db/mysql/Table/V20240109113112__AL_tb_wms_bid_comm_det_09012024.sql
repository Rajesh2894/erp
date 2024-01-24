--liquibase formatted sql
--changeset PramodPatil:V20240109113112__AL_tb_wms_bid_comm_det_09012024.sql
ALTER TABLE tb_wms_bid_comm_det MODIFY quoted_price DECIMAL(15, 2) NULL default NULL;
