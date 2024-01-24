--liquibase formatted sql
--changeset PramodPatil: V20230720201318__AL_tb_dm_complain_register_20072023.sql
alter table tb_dm_complain_register add column complainer_mobile1 varchar(15) null default null;