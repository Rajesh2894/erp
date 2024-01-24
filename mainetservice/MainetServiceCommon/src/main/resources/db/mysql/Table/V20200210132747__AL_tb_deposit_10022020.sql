--liquibase formatted sql
--changeset Anil:V20200210132747__AL_tb_deposit_10022020.sql
ALTER TABLE tb_deposit CHANGE COLUMN DEP_NARRATION DEP_NARRATION VARCHAR(500) NOT NULL COMMENT 'Deposite Narration' ;
