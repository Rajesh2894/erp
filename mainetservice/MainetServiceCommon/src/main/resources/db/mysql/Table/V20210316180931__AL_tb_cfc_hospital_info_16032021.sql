--liquibase formatted sql
--changeset Kanchan:V20210316180931__AL_tb_cfc_hospital_info_16032021.sql
alter table tb_cfc_hospital_info modify column business_strt_dt datetime NULL;
