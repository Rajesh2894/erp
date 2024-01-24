--liquibase formatted sql
--changeset Kanchan:V20210615171559__AL_tb_as_pro_assesment_owner_dtl_15062021.sql
alter table tb_as_pro_assesment_owner_dtl add column PRO_ASSO_MOBILENO_OTP bigint(12) default null;
