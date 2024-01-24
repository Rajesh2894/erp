--liquibase formatted sql
--changeset Anil:V20200127160855__AL_tb_ml_trade_mast_27012020.sql
ALTER TABLE tb_ml_trade_mast CHANGE COLUMN TRD_STATUS TRD_STATUS CHAR(10) NOT NULL;
