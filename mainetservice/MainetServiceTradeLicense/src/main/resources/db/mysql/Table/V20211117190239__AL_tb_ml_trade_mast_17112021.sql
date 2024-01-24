--liquibase formatted sql
--changeset Kanchan:V20211117190239__AL_tb_ml_trade_mast_17112021.sql
alter table tb_ml_trade_mast add column SOURCE varchar(10) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211117190239__AL_tb_ml_trade_mast_171120211.sql
alter table tb_ml_trade_mast_hist add column SOURCE varchar(10) NULL DEFAULT NULL;
