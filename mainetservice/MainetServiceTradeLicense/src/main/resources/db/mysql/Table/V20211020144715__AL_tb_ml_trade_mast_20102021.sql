--liquibase formatted sql
--changeset Kanchan:V20211020144715__AL_tb_ml_trade_mast_20102021.sql
alter table tb_ml_trade_mast add column DUE_ON_WATER decimal(15,3),add PROP_OWNER_ADDRS varchar(200) NULL;
--liquibase formatted sql
--changeset Kanchan:V20211020144715__AL_tb_ml_trade_mast_201020211.sql
alter table tb_ml_trade_mast_hist add column DUE_ON_WATER decimal(15,3),add PROP_OWNER_ADDRS varchar(200) NULL;
