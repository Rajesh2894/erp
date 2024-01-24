--liquibase formatted sql
--changeset Kanchan:V20210211184302__AL_tb_cfc_hospital_info_11022021.sql
alter table tb_cfc_hospital_info add column ORGID bigInt(12) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210211184302__AL_tb_cfc_hospital_info_110220211.sql
alter table tb_cfc_hospital_info_det add column ORGID bigInt(12) NOT NULL;
