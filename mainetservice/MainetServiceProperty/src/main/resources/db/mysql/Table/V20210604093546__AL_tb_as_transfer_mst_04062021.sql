--liquibase formatted sql
--changeset Kanchan:V20210604093546__AL_tb_as_transfer_mst_04062021.sql
alter table tb_as_transfer_mst  add column REGISTRATION_NO  varchar(50)  NULL DEFAULT NULL;
