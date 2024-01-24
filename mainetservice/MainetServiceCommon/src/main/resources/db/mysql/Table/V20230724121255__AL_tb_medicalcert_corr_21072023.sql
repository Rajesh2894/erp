--liquibase formatted sql
--changeset PramodPatil: V20230724121255__AL_tb_medicalcert_corr_21072023.sql 
ALTER TABLE tb_medicalcert_corr modify column mc_death_manner varchar(50) null default null;

--liquibase formatted sql
--changeset PramodPatil: V20230724121255__AL_tb_medicalcert_corr_210720231.sql 
ALTER TABLE tb_medicalcert_corr_history modify column mc_death_manner varchar(50) null default null;

--liquibase formatted sql
--changeset PramodPatil: V20230724121255__AL_tb_medicalcert_corr_210720232.sql 
ALTER TABLE tb_medicalcert_history modify column mc_death_manner varchar(50) null default null;