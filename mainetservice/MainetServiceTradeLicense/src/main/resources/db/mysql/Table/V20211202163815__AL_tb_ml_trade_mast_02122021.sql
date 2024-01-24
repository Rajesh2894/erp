--liquibase formatted sql
--changeset Kanchan:V20211202163815__AL_tb_ml_trade_mast_02122021.sql
alter table tb_ml_trade_mast modify column TRD_FRE_NOC varchar(30) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211202163815__AL_tb_ml_trade_mast_021220211.sql
alter table tb_ml_trade_mast_hist modify column TRD_FRE_NOC varchar(30) NULL DEFAULT NULL;

