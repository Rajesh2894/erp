--liquibase formatted sql
--changeset Kanchan:V20210315194512__AL_tb_ml_trade_mast_hist_15032021.sql
Alter table  tb_ml_trade_mast_hist

add column Trans_Mode_TYPE bigint(12) NULL;
