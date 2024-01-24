--liquibase formatted sql
--changeset Anil:V20190621153149__AL_tb_csmr_info_hist_21062019.sql
ALTER TABLE tb_csmr_info_hist ADD COLUMN CS_ILLEGAL_NOTICE_NO VARCHAR(50) NULL AFTER CS_PTYPE;
--liquibase formatted sql
--changeset Anil:V20190621153149__AL_tb_csmr_info_hist_210620191.sql
ALTER TABLE tb_csmr_info_hist ADD COLUMN CS_ILLEGAL_NOTICE_DATE DATETIME NULL AFTER CS_ILLEGAL_NOTICE_NO;
