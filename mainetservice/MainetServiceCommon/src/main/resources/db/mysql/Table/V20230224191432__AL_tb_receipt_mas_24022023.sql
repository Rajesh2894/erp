--liquibase formatted sql
--changeset Kanchan:V20230224191432__AL_tb_receipt_mas_24022023.sql
ALTER TABLE tb_receipt_mas modify COLUMN RM_NARRATION varchar(1000) NOT NULL;