--liquibase formatted sql
--changeset Anil:V20191121192024__AL_tb_ac_lnmst_21112019.sql
ALTER TABLE tb_ac_lnmst ADD COLUMN LN_PERIOD_UNIT VARCHAR(50) NOT NULL AFTER LN_NO;


