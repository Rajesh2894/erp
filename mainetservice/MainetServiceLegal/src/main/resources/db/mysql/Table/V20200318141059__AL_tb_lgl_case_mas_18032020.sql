--liquibase formatted sql
--changeset Anil:V20200318141059__AL_tb_lgl_case_mas_18032020.sql
ALTER TABLE tb_lgl_case_mas ADD COLUMN concerned_ulb BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200318141059__AL_tb_lgl_case_mas_180320201.sql
ALTER TABLE tb_lgl_case_mas_hist ADD COLUMN concerned_ulb BIGINT(12) NULL;
