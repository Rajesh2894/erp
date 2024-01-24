--liquibase formatted sql
--changeset PramodPatil:V20231121152307__AL_Tb_Sfac_Sale_Info_Detail_21112023.sql
alter table Tb_Sfac_Sale_Info_Detail
add column ITEM_TYPE BigInt(20) null default null,
add column VARIE_TYPE varchar(100) null default null,
add column SALES_TYPE BigInt(20) null default null,
add column CHANNEL BigInt(20) null default null,
add column TOT_QUANT BigInt(20) null default null,
add column TOT_VAL decimal(15,2) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231121152307__AL_Tb_Sfac_Sale_Info_Detail_211120231.sql
alter table Tb_Sfac_Sale_Info_Detail_Hist
add column ITEM_TYPE BigInt(20) null default null,
add column VARIE_TYPE varchar(100) null default null,
add column SALES_TYPE BigInt(20) null default null,
add column CHANNEL BigInt(20) null default null,
add column TOT_QUANT BigInt(20) null default null,
add column TOT_VAL decimal(15,2) null default null;