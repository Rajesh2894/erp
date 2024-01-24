--liquibase formatted sql
--changeset Kanchan:V20211201192146__AL_tb_csmr_info_01122021.sql
alter table tb_csmr_info add column HOUSE_NUMBER varchar(30) NULL DEFAULT NULL;
