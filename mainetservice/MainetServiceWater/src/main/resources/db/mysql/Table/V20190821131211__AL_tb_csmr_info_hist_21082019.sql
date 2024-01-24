--liquibase formatted sql
--changeset Anil:V20190821131211__AL_tb_csmr_info_hist_21082019.sql
ALTER TABLE tb_csmr_info_hist ADD COLUMN CS_DEPOSIT_DATE DATE NULL AFTER CS_DEPOSIT_AMOUNT;
