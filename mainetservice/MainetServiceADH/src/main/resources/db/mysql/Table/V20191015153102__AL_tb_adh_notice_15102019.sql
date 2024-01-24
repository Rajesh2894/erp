--liquibase formatted sql
--changeset Anil:V20191015153102__AL_tb_adh_notice_15102019.sql
ALTER TABLE tb_adh_notice CHANGE COLUMN notice_no notice_no VARCHAR(25) NOT NULL COMMENT 'notice number';
