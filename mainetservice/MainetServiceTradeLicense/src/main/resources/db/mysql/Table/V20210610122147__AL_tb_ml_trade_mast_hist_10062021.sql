--liquibase formatted sql
--changeset Kanchan:V20210610122147__AL_tb_ml_trade_mast_hist_10062021.sql
alter table tb_ml_trade_mast_hist add column H_STATUS char(1) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210610122147__AL_tb_ml_trade_mast_hist_100620211.sql
alter table tb_ml_item_detail_hist add column H_STATUS char(1) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210610122147__AL_tb_ml_trade_mast_hist_100620212.sql
alter table tb_ml_owner_detail_hist add column H_STATUS char(1) NULL;
