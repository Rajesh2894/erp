--liquibase formatted sql
--changeset Kanchan:V20210119121945__AL_tb_csmr_info_19012021.sql
alter table tb_csmr_info add column con_active  Varchar(1) Default null;
