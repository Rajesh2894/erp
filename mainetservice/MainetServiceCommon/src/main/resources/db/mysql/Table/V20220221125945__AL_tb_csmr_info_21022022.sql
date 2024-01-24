--liquibase formatted sql
--changeset Kanchan:V20220221125945__AL_tb_csmr_info_21022022.sql
alter table tb_csmr_info add column HOUSE_NUMBER_BILLING varchar(30) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220221125945__AL_tb_csmr_info_210220221.sql
alter table tb_csmr_info add column FATHER_GUARDIAN_NAME_BILLING varchar(300) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220221125945__AL_tb_csmr_info_210220222.sql
alter table tb_csmr_info add column LANDMARK_BILLING varchar(300) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220221125945__AL_tb_csmr_info_210220223.sql
alter table tb_csmr_info add column MOBILE_BILLING varchar(15) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220221125945__AL_tb_csmr_info_210220224.sql
alter table tb_csmr_info add column EMAIL_BILLING varchar(300) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220221125945__AL_tb_csmr_info_210220225.sql
alter table tb_csmr_info add column AADHAR_BILLING varchar(30) NULL DEFAULT NULL;
