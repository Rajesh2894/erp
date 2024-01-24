--liquibase formatted sql
--changeset Anil:V20200203125225__AL_tb_swd_rtgs_payment_03022020.sql
ALTER TABLE tb_swd_rtgs_payment CHANGE COLUMN WORK_ORDER_NUMBER WORK_ORDER_NUMBER VARCHAR(100) NOT NULL;
