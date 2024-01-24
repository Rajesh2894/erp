--liquibase formatted sql
--changeset Kanchan:V20230324133324__AL_tb_sfac_fpo_master_24032023.sql
ALTER TABLE tb_sfac_fpo_master ADD UNIQUE INDEX COMPANY_REG_NO_UNIQUE (COMPANY_REG_NO ASC);