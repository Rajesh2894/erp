--liquibase formatted sql
--changeset Anil:V20200120180121__AL_tb_seq_configdet_20012020.sql
ALTER TABLE tb_seq_configdet
DROP COLUMN DISP_PVLE,
CHANGE COLUMN PREFIX_ID PREFIX_ID CHAR(5) NULL DEFAULT NULL COMMENT 'Prefix Name' ;

