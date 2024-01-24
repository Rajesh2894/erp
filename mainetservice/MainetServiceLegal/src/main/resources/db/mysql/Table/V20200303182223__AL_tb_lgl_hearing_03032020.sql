--liquibase formatted sql
--changeset Anil:V20200303182223__AL_tb_lgl_hearing_03032020.sql
ALTER TABLE tb_lgl_hearing CHANGE COLUMN hr_proceeding hr_proceeding VARCHAR(1000) NULL DEFAULT NULL COMMENT 'Hearing Procedding\n' ;
