--liquibase formatted sql
--changeset Anil:V20190917114034__AL_tb_lgl_case_mas_17092019.sql
ALTER TABLE tb_lgl_case_mas 
CHANGE COLUMN loc_id loc_id BIGINT(12) NULL COMMENT 'Location',
ADD COLUMN CSE_CITY VARCHAR(250) NULL AFTER CSE_STAT;
