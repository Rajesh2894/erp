--liquibase formatted sql
--changeset Kanchan:V20210531133206__AL_tb_as_assesment_owner_dtl_31052021.sql
alter table tb_as_assesment_owner_dtl add column MN_ASSO_MOBILENO_OTP bigint(12)  default null;
