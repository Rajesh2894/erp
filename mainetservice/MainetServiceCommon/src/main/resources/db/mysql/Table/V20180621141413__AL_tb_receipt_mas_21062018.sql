--liquibase formatted sql
--changeset nilima:V20180621141413__AL_tb_receipt_mas_21062018.sql
ALTER TABLE tb_receipt_mas 
CHANGE COLUMN RECEIPT_TYPE_FLAG RECEIPT_TYPE_FLAG CHAR(3) NULL DEFAULT NULL ;