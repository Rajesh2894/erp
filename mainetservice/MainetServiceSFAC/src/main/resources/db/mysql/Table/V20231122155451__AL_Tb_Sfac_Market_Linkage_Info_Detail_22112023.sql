--liquibase formatted sql
--changeset PramodPatil:V20231122155451__AL_Tb_Sfac_Market_Linkage_Info_Detail_22112023.sql
alter table Tb_Sfac_Market_Linkage_Info_Detail add column MR_PLAT_OTHER varchar(100) null default null;
alter table Tb_Sfac_Market_Linkage_Info_Detail_Hist add column MR_PLAT_OTHER varchar(100) null default null;