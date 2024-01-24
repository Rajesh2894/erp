--liquibase formatted sql
--changeset PramodPatil:V20230822111126_AL_tb_receipt_mas_22082023.sql
alter table tb_receipt_mas add column RM_ADDRESS varchar(500) null default null;
