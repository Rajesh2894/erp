--liquibase formatted sql
--changeset Kanchan:V20230206100824__AL_tb_csmr_info_06022023.sql
alter table tb_csmr_info add column cs_gender bigint(20) null default null;