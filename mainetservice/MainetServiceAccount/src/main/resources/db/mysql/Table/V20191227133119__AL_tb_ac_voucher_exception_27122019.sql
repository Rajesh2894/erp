--liquibase formatted sql
--changeset Anil:V20191227133119__AL_tb_ac_voucher_exception_27122019.sql
ALTER TABLE tb_ac_voucher_exception CHANGE COLUMN EXCEPTION_DETAILS EXCEPTION_DETAILS VARCHAR(3000) NULL DEFAULT NULL COMMENT 'Exception details' ;
