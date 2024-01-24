--liquibase formatted sql
--changeset PramodPatil:V20231016163352__AL_tb_rts_deathregcert_16102023.sql
alter table tb_rts_deathregcert add column APPLICANT_HWNAME varchar(100) null default null;
alter table tb_rts_deathregcert add column DR_CGD date null default null;