--liquibase formatted sql
--changeset PramodPatil:V20231212121833__AL_tb_sfac_equity_info_detail_12122023.sql
alter table tb_sfac_equity_info_detail add column TOT_EQUITY_GR_REC decimal(15,2) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231212121833__AL_tb_sfac_equity_info_detail_121220231.sql
alter table tb_sfac_equity_info_detail_hist add column TOT_EQUITY_GR_REC decimal(15,2) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231212121833__AL_tb_sfac_equity_info_detail_121220232.sql
alter table tb_sfac_equity_info_detail add column TOT_EQUITY decimal(15,2) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231212121833__AL_tb_sfac_equity_info_detail_121220233.sql
alter table tb_sfac_equity_info_detail_hist add column TOT_EQUITY decimal(15,2) null default null;