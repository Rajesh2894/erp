--liquibase formatted sql
--changeset PramodPatil:V20231214161541__AL_Tb_SFAC_Cbbo_Mast_Det_14122023.sql
alter table Tb_SFAC_Cbbo_Mast_Det add column SOC_CAT bigint(20) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231214161541__AL_Tb_SFAC_Cbbo_Mast_Det_141220231.sql
alter table TB_SFAC_CBBO_DET_HIST add column SOC_CAT bigint(20) null default null;