--liquibase formatted sql
--changeset PramodPatil:V20240112184840__AL_tb_scrutiny_values_hist_12012024.sql
alter table tb_scrutiny_values_hist add column TASK_ID bigint(20) NULL DEFAULT NULL;
