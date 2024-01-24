--liquibase formatted sql
--changeset priya:V20180303121751__AL_tb_ac_payment_mas_01032018.sql
ALTER TABLE tb_ac_payment_mas DROP LANG_ID;
--liquibase formatted sql
--changeset priya:V20180303121751__AL_tb_ac_payment_mas_010320181.sql
ALTER TABLE tb_ac_payment_mas 
ADD COLUMN PDM_ID BIGINT(12) NULL ;
