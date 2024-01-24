--liquibase formatted sql
--changeset PramodPatil:V20231214161636__AL_tb_sfac_fpo_administrative_det_14122023.sql
alter table tb_sfac_fpo_administrative_det add column SOC_CAT bigint(20) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231214161636__AL_tb_sfac_fpo_administrative_det_141220231.sql
alter table tb_sfac_fpo_administrative_det_hist add column SOC_CAT bigint(20) null default null;