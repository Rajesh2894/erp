--liquibase formatted sql
--changeset Kanchan:V20211102161900__AL_tb_csmr_info_02112021.sql
alter table tb_csmr_info  add column PM_FLAT_NO varchar (30) null default null;
--liquibase formatted sql
--changeset Kanchan:V20211102161900__AL_tb_csmr_info_021120211.sql
alter table tb_csmr_info_hist   add column PM_FLAT_NO varchar (30) null default null;
