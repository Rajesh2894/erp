--liquibase formatted sql
--changeset Kanchan:V20220317195952__AL_tb_receipt_mas_17032022.sql
alter table tb_receipt_mas add column POS_PAY_TXNID varchar(100) NULL DEFAULT NULL;
