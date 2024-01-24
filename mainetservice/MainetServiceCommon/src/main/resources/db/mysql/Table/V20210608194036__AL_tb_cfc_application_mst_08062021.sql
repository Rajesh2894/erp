--liquibase formatted sql
--changeset Kanchan:V20210608194036__AL_tb_cfc_application_mst_08062021.sql
alter table tb_cfc_application_mst modify column APM_SEX varchar(12);
