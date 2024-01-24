--liquibase formatted sql
--changeset Kanchan:V20220308101542__AL_tb_receipt_mas_08032022.sql
alter table  tb_receipt_mas  add column POS_PAY_MODE varchar(50) null default null;
