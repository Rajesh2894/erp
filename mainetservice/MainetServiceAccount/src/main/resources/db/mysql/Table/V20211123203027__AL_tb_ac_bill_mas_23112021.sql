--liquibase formatted sql
--changeset Kanchan:V20211123203027__AL_tb_ac_bill_mas_23112021.sql
ALTER TABLE  tb_ac_bill_mas
ADD COLUMN `DUE_DATE` DATE NULL AFTER `FUND_ID`;
