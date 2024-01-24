--liquibase formatted sql
--changeset jinea:V20180105111604__AL_FK_tb_ac_bill_exp_detail.sql
ALTER TABLE tb_ac_bill_exp_detail ADD CONSTRAINT F_BM_ID
FOREIGN KEY (BM_ID) REFERENCES tb_ac_bill_mas(BM_ID)
ON DELETE NO ACTION ON UPDATE NO ACTION;
