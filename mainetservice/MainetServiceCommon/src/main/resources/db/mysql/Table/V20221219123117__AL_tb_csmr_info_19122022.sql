--liquibase formatted sql
--changeset Kanchan:V20221219123117__AL_tb_csmr_info_19122022.sql
alter table tb_csmr_info add column con_relation_name varchar(500) null default null;