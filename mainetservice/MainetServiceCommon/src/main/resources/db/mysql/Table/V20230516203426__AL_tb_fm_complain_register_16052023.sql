--liquibase formatted sql
--changeset Kanchan:V20230516203426__AL_tb_fm_complain_register_16052023.sql
alter table tb_fm_complain_register
add column vehicle_in_date date null default null,
add column vehicle_out_date date null default null;