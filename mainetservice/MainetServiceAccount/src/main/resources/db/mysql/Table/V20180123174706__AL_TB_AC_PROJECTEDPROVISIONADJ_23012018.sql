--liquibase formatted sql
--changeset priya:V20180123174706__AL_TB_AC_PROJECTEDPROVISIONADJ_23012018
ALTER TABLE tb_ac_projectedprovisionadj 
CHANGE COLUMN FI04_LO1 AUTH_FLG CHAR(1) CHARACTER SET 'latin1' NOT NULL COMMENT 'Authorization Flag' ;


