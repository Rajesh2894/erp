--liquibase formatted sql
--changeset Anil:V20190919132207__AL_tb_ml_trade_mast_19092019.sql
ALTER TABLE tb_ml_trade_mast ADD COLUMN TRD_FLAT_NO VARCHAR(250) NULL AFTER LG_IP_MAC_UPD;
