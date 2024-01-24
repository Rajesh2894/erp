--liquibase formatted sql
--changeset Kanchan:V20221207194209__AL_tb_dm_complain_register_07122022.sql
alter table tb_dm_complain_register add column caller_area varchar(1) NULL default null;