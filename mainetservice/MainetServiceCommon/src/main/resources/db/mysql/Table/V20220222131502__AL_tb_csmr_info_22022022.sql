--liquibase formatted sql
--changeset Kanchan:V20220222131502__AL_tb_csmr_info_22022022.sql
alter table tb_csmr_info add column FATHER_GUARDIAN_NAME varchar(300) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220222131502__AL_tb_csmr_info_220220221.sql
alter table tb_csmr_info add column LANDMARK varchar(300) NULL DEFAULT NULL;
