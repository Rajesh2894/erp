--liquibase formatted sql
--changeset jinea:V20180105111510__AL_DROP_FK_tb_ac_voucher.sql
ALTER TABLE tb_ac_voucher 
DROP FOREIGN KEY FK_VOUCHERHD_FIELD_ID;

--liquibase formatted sql
--changeset jinea:V20180105111510__AL_DROP_FK_tb_ac_voucher1.sql
ALTER TABLE tb_ac_voucher 
DROP INDEX FK_VOUCHERHD_FIELD_ID ;



