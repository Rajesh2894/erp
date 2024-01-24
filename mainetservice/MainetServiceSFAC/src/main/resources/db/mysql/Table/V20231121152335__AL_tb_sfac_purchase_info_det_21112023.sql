--liquibase formatted sql
--changeset PramodPatil:V20231121152335__AL_tb_sfac_purchase_info_det_21112023.sql
alter table tb_sfac_purchase_info_det
add column ITEM_TYPE BigInt(20) null default null,
add column VARIE_TYPE varchar(100) null default null,
add column TOT_QUANT BigInt(20) null default null,
add column TOT_VAL decimal(15,2) null default null,
add column NO_FARM_PROD_FROM BigInt(20) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231121152335__AL_tb_sfac_purchase_info_det_211120231.sql
alter table tb_sfac_purchase_info_det_hist
add column ITEM_TYPE BigInt(20) null default null,
add column VARIE_TYPE varchar(100) null default null,
add column TOT_QUANT BigInt(20) null default null,
add column TOT_VAL decimal(15,2) null default null,
add column NO_FARM_PROD_FROM BigInt(20) null default null;