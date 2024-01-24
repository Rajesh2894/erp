--liquibase formatted sql
--changeset Kanchan:V20220114175718__AL_tb_receipt_mas_14012022.sql
alter table  tb_receipt_mas add column RM_CFC_CNTRID  bigint(15)  DEFAULT NULL;
