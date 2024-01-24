--liquibase formatted sql
--changeset Kanchan:V20221128105307__AL_tb_csmr_info_28112022.sql
alter table tb_csmr_info add column floor_no bigint(20) null default null,
add column built_up_area decimal(15,2) null default null,
add column noc_appl varchar(5) null default null;