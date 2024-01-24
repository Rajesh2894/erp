--liquibase formatted sql
--changeset PramodPatil:V20240108180832__AL_tb_scrutiny_values_hist_08012024.sql
alter table tb_scrutiny_values_hist add column REMARK varchar(500) null default null; 

--liquibase formatted sql
--changeset PramodPatil:V20240108180832__AL_tb_scrutiny_values_hist_080120241.sql
alter table tb_scrutiny_values add column REMARK varchar(500) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20240108180832__AL_tb_scrutiny_values_hist_080120242.sql
alter table tb_scrutiny_labels add column SL_QUERY varchar(500) null default null;