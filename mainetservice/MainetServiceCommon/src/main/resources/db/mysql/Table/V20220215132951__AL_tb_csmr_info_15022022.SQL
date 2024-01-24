--liquibase formatted sql
--changeset Kanchan:V20220215132951__AL_tb_csmr_info_15022022.SQL
alter table tb_csmr_info add column HOUSE_NUMBER_BILLING varchar(30) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215132951__AL_tb_csmr_info_150220221.SQL
alter table tb_csmr_info add column FATHER_GUARDIAN_NAME_BILLING varchar(300) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215132951__AL_tb_csmr_info_150220222.SQL
alter table tb_csmr_info add column LANDMARK_BILLING varchar(300) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215132951__AL_tb_csmr_info_150220223.SQL
alter table tb_csmr_info add column MOBILE_BILLING varchar(15) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215132951__AL_tb_csmr_info_150220224.SQL
alter table tb_csmr_info add column EMAIL_BILLING varchar(300) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20220215132951__AL_tb_csmr_info_150220225.SQL
alter table tb_csmr_info add column AADHAR_BILLING varchar(30) NULL DEFAULT NULL;
