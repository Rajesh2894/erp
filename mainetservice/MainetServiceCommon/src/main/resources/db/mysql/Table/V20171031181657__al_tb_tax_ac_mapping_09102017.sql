--liquibase formatted sql
--changeset nilima:V20171031181657__al_tb_tax_ac_mapping_09102017.sql
ALTER TABLE tb_tax_budget_code
CHANGE COLUMN budgetcode_id SAC_HEAD_ID BIGINT(15) NOT NULL COMMENT 'foregin key (TB_AC_SECONDARYHEAD_MASTER)' ,
ADD INDEX FK_TAX_SAC_HEAD_ID_idx (SAC_HEAD_ID ASC)  COMMENT '', RENAME TO  tb_tax_ac_mapping ;
ALTER TABLE tb_tax_ac_mapping 
ADD CONSTRAINT FK_TAX_SAC_HEAD_ID
  FOREIGN KEY (SAC_HEAD_ID)
  REFERENCES tb_ac_secondaryhead_master (SAC_HEAD_ID)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;