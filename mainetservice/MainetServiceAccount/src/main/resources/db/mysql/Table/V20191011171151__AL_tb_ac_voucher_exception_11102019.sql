--liquibase formatted sql
--changeset Anil:V20191011171151__AL_tb_ac_voucher_exception_11102019.sql
ALTER TABLE tb_ac_voucher_exception CHANGE COLUMN EXCEPTION_DETAILS EXCEPTION_DETAILS VARCHAR(500) NULL DEFAULT NULL COMMENT 'Exception details';
