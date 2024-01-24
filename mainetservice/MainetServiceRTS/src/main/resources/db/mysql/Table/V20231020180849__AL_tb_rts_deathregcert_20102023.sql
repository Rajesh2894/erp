--liquibase formatted sql
--changeset PramodPatil:V20231020180849__AL_tb_rts_deathregcert_20102023.sql
alter Table tb_rts_deathregcert
add column APPLICANT_HWNAME varchar(100) null default null,
add column DR_CGD date not null;

--liquibase formatted sql
--changeset PramodPatil:V20231020180849__AL_tb_rts_deathregcert_201020231.sql
alter Table tb_rts_birthregcert add column BR_ACERT_GEN_DTE date not null;