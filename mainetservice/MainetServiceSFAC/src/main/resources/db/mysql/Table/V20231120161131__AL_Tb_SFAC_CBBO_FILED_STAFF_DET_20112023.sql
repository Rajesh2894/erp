--liquibase formatted sql
--changeset PramodPatil:V20231120161131__AL_Tb_SFAC_CBBO_FILED_STAFF_DET_20112023.sql
alter table Tb_SFAC_CBBO_FILED_STAFF_DET add column STATUS varchar(10) null default 'A';

--liquibase formatted sql
--changeset PramodPatil:V20231120161131__AL_Tb_SFAC_CBBO_FILED_STAFF_DET_201120231.sql
alter table Tb_Sfac_Equity_Grant_Det add column GENDER_ID BigInt(20) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231120161131__AL_Tb_SFAC_CBBO_FILED_STAFF_DET_201120232.sql
alter table Tb_Sfac_Equity_Grant_Det_hist add column GENDER_ID BigInt(20) null default null;
