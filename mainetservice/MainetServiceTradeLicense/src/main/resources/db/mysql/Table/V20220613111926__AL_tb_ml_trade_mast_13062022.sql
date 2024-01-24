--liquibase formatted sql
--changeset Kanchan:V20220613111926__AL_tb_ml_trade_mast_13062022.sql
Alter table tb_ml_trade_mast change  column TRD_BUSNM TRD_BUSNM  varchar(200);
