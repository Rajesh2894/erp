--liquibase formatted sql
--changeset Kanchan:V20210915173522__AL_tb_cfc_hospital_info_15092021.sql
alter table tb_cfc_hospital_info modify column cont_no_clinic bigint(12),modify cont_no_hsptl bigint(12) NULL;
