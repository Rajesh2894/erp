--liquibase formatted sql
--changeset Anil:V20200206172343__AL_tb_seq_configmaster_06022020.sql
ALTER TABLE tb_seq_configmaster CHANGE COLUMN SEQ_SEP SEQ_SEP BIGINT(12) NULL DEFAULT NULL COMMENT 'Separator';
