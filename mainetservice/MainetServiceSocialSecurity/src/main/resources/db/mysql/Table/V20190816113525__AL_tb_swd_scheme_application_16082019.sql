--liquibase formatted sql
--changeset Anil:V20190816113525__AL_tb_swd_scheme_application_16082019.sql
ALTER TABLE tb_swd_scheme_application ADD COLUMN SAPI_UID VARCHAR(12) NULL COMMENT 'Adhar Number';
