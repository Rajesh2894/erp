--liquibase formatted sql
--changeset Kanchan:V20211109165957__AL_tb_ml_trade_mast_09112021.sql
alter table tb_ml_trade_mast add column PINCODE int(11),add LAND_MARK  varchar(250),add  DIRECTORS_NAME_ADDRES varchar(500) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211109165957__AL_tb_ml_trade_mast_091120211.sql
alter table tb_ml_trade_mast_hist add column PINCODE int(11),add LAND_MARK  varchar(250),add  DIRECTORS_NAME_ADDRES varchar(500) DEFAULT NULL;
