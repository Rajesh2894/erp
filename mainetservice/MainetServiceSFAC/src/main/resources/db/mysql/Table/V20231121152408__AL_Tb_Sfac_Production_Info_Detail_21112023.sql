--liquibase formatted sql
--changeset PramodPatil:V20231121152408__AL_Tb_Sfac_Production_Info_Detail_21112023.sql
alter table Tb_Sfac_Production_Info_Detail add column NO_FARM_INV_PROD BigInt(20) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231121152408__AL_Tb_Sfac_Production_Info_Detail_211120231.sql
alter table Tb_Sfac_Production_Info_Detail_Hist add column NO_FARM_INV_PROD BigInt(20) null default null;