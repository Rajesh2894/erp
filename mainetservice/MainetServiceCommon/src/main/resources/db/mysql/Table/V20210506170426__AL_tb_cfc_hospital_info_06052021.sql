--liquibase formatted sql
--changeset Kanchan:V20210506170426__AL_tb_cfc_hospital_info_06052021.sql
 alter  table tb_cfc_hospital_info modify column vstng_doctr_nm varchar(50) NULL
