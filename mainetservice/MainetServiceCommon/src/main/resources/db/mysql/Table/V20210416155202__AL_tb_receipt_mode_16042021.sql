--liquibase formatted sql
--changeset Kanchan:V20210416155202__AL_tb_receipt_mode_16042021.sql
alter table  tb_receipt_mode  modify RD_CHEQUE_STATUS  bigint (12) NULL DEFAULT NULL;
