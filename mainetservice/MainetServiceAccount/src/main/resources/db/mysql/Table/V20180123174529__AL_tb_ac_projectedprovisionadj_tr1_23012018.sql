--liquibase formatted sql
--changeset priya:V20180123174529__AL_tb_ac_projectedprovisionadj_tr1_23012018
ALTER TABLE tb_ac_projectedprovisionadj_tr 
CHANGE COLUMN FI04_LO1 AUTH_FLG CHAR(1) NOT NULL COMMENT 'Authorization Flag' ;


