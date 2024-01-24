--liquibase formatted sql
--changeset Kanchan:V20220509173227__AL_tb_onl_tran_mas_service_09052022.sql
alter table  tb_onl_tran_mas_service add column PROP_FLAT_NO  varchar(50) null default null;
