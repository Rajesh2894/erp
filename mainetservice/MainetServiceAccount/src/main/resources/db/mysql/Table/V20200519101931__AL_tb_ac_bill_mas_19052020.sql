--liquibase formatted sql
--changeset Anil:V20200519101931__AL_tb_ac_bill_mas_19052020.sql
ALTER TABLE tb_ac_bill_mas 
ADD COLUMN BM_TAXABLE_VAL DECIMAL(15,2) NULL,
ADD COLUMN FUND_ID BIGINT(12) NULL ,
ADD INDEX FK_FUND_ID_idx (FUND_ID ASC);
--liquibase formatted sql
--changeset Anil:V20200519101931__AL_tb_ac_bill_mas_190520201.sql
ALTER TABLE tb_ac_bill_mas ADD CONSTRAINT FK_FUND_ID FOREIGN KEY (FUND_ID) REFERENCES tb_ac_fund_master (FUND_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
--liquibase formatted sql
--changeset Anil:V20200519101931__AL_tb_ac_bill_mas_190520202.sql
ALTER TABLE tb_ac_bill_mas_hist 
ADD COLUMN BM_TAXABLE_VAL DECIMAL(15,2) NULL,
ADD COLUMN FUND_ID BIGINT(12) NULL;
