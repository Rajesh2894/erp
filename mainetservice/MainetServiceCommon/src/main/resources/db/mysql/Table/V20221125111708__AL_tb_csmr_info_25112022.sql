--liquibase formatted sql
--changeset Kanchan:V20221125111708__AL_tb_csmr_info_25112022.sql
alter table tb_csmr_info add column con_relation_id bigint(20) null default null,
add column no_of_toilet_seats bigint(20) null default null;