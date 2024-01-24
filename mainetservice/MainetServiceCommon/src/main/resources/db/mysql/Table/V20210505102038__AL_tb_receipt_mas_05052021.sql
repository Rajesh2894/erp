--liquibase formatted sql
--changeset Kanchan:V20210505102038__AL_tb_receipt_mas_05052021.sql
alter table tb_receipt_mas add column flat_no varchar(30) NULL DEFAULT NULL;
