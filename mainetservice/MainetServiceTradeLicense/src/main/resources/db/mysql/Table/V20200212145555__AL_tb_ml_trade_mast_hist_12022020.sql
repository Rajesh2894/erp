--liquibase formatted sql
--changeset Anil:V20200212145555__AL_tb_ml_trade_mast_hist_12022020.sql
ALTER TABLE tb_ml_trade_mast_hist CHANGE COLUMN TRD_STATUS TRD_STATUS BIGINT(12) NOT NULL;
