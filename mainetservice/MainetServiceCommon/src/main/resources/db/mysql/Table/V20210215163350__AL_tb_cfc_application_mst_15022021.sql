--liquibase formatted sql
--changeset Kanchan:V20210215163350__AL_tb_cfc_application_mst_15022021.sql
ALTER TABLE tb_cfc_application_mst ADD COLUMN APM_STATUS CHAR(1)  NULL; 
