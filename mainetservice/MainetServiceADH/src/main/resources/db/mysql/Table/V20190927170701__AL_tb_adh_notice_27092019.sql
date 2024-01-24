--liquibase formatted sql
--changeset Anil:V20190927170701__AL_tb_adh_notice_27092019.sql
ALTER TABLE tb_adh_notice
CHANGE COLUMN remarks remarks VARCHAR(200) NULL COMMENT 'remarks' ,
CHANGE COLUMN created_date created_date DATE NOT NULL COMMENT 'last modification date' ,
CHANGE COLUMN created_by created_by BIGINT(12) NOT NULL COMMENT 'User Identity' ;
