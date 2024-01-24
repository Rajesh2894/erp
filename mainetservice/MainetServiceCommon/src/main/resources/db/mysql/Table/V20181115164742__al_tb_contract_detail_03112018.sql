--liquibase formatted sql
--changeset nilima:V20181115164742__al_tb_contract_detail_03112018.sql
ALTER TABLE tb_contract_detail
ADD COLUMN CONT_ADDPER_SECURITYDE DECIMAL(10,2) NULL COMMENT 'Additional Performance Security Depoist' AFTER `CONT_SEC_REC_DATE`,
ADD COLUMN CONT_OTHER_DEPDET DECIMAL(10,2) NULL COMMENT 'Other Deposit Detail' AFTER `CONT_ADDPER_SECURITYDE`;
