--liquibase formatted sql
--changeset Kanchan:V20220629202647__AL_tb_ac_payment_mas_29062022.sql
alter table tb_ac_payment_mas  modify column DPAYBILLREF_NO varchar(30) null default null;