--liquibase formatted sql
--changeset Kanchan:V20211209182507__AL_tb_cfc_application_mst_hist_09122021.sql
alter table tb_cfc_application_mst_hist add column APM_SOURCE char(2) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211209182507__AL_tb_cfc_application_mst_hist_091220211.sql
alter table tb_cfc_application_mst add  column APM_SOURCE char(2) DEFAULT NULL;
