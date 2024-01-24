--liquibase formatted sql
--changeset Kanchan:V20221128170433__AL_tb_csmr_info_28112022.sql
alter table tb_csmr_info add column estimate_charge decimal(15,2) null default null;