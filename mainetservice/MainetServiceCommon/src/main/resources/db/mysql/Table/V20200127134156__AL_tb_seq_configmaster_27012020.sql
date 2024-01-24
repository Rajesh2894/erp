--liquibase formatted sql
--changeset Anil:V20200127134156__AL_tb_seq_configmaster_27012020.sql
ALTER TABLE tb_seq_configmaster CHANGE COLUMN SEQ_NAME SEQ_NAME BIGINT(12) NOT NULL COMMENT 'Sequence Name' ;
--liquibase formatted sql
--changeset Anil:V20200127134156__AL_tb_seq_configmaster_270120201.sql
ALTER TABLE tb_seq_configmaster
ADD COLUMN UPDATED_BY BIGINT(12) NULL AFTER DUE_DATE,
ADD COLUMN UPDATED_DATE DATETIME NULL AFTER UPDATED_BY;

