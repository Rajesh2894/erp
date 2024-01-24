--liquibase formatted sql
--changeset Anil:V20190806133411__AL_tb_csmr_info_hist_06082019.sql
ALTER TABLE tb_csmr_info_hist
ADD COLUMN CS_DEPOSIT_AMOUNT DECIMAL(15,2) NULL AFTER CS_ILLEGAL_NOTICE_DATE;

