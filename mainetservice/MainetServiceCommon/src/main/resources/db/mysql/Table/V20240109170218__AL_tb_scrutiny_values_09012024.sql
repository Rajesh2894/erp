--liquibase formatted sql
--changeset PramodPatil:V20240109170218__AL_tb_scrutiny_values_09012024.sql
alter table tb_scrutiny_values add column TASK_ID bigint(20);