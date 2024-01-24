--liquibase formatted sql
--changeset nilima:V20181124184105__AL_tb_sw_bill_mas_20112018.sql
ALTER TABLE tb_sw_bill_mas
ADD COLUMN MANUAL_BOOKNO VARCHAR(50) NULL AFTER LAST_PAYMENT_UPTO;

--liquibase formatted sql
--changeset nilima:V20181124184105__AL_tb_sw_bill_mas_201120181.sql
ALTER TABLE tb_sw_bill_mas_hist
ADD COLUMN MANUAL_BOOKNO VARCHAR(50) NULL AFTER LAST_PAYMENT_UPTO;