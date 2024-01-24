--liquibase formatted sql
--changeset Kanchan:V20211202204609__AL_tb_lgl_case_mas_02122021.sql
alter table tb_lgl_case_mas add column  LOCID bigint(12) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211202204609__AL_tb_lgl_case_mas_021220211.sql
alter table tb_lgl_case_mas_hist add column  LOCID bigint(12) NULL DEFAULT NULL;
