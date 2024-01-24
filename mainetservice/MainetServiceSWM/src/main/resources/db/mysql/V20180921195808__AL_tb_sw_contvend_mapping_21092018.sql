--liquibase formatted sql
--changeset nilima:V20180921195808__AL_tb_sw_contvend_mapping_21092018.sql
ALTER TABLE tb_sw_contvend_mapping
CHANGE COLUMN RO_ID BEAT_ID BIGINT(12) NULL DEFAULT NULL COMMENT 'Beat No.' ;
