--liquibase formatted sql
--changeset PramodPatil:V20231030173012__AL_Tb_Sfac_Equity_Grant_tranche_det_30102023.sql
alter table Tb_Sfac_Equity_Grant_tranche_det add column REFERENCE_NO varchar(100) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231030173012__AL_Tb_Sfac_Equity_Grant_tranche_det_301020231.sql
alter table Tb_Sfac_Equity_Grant_tranche_det_hist add column REFERENCE_NO varchar(100) null default null;