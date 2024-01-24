--liquibase formatted sql
--changeset Anil:V20190617174103__AL_tb_lgl_arbitory_fee_13062019.sql
ALTER TABLE tb_lgl_arbitory_fee 
ADD COLUMN CSE_ID BIGINT(12) NOT NULL AFTER ARB_ID,
ADD INDEX fk_abcse_id_idx (CSE_ID ASC);
--liquibase formatted sql
--changeset Anil:V20190617174103__AL_tb_lgl_arbitory_fee_130620191.sql
ALTER TABLE tb_lgl_arbitory_fee 
ADD CONSTRAINT fk_abcse_id
  FOREIGN KEY (CSE_ID)
  REFERENCES tb_lgl_case_mas (cse_id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;