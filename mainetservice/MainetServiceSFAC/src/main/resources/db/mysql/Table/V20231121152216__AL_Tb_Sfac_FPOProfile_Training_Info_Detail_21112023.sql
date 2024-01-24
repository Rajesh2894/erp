--liquibase formatted sql
--changeset PramodPatil:V20231121152216__AL_Tb_Sfac_FPOProfile_Training_Info_Detail_21112023.sql
alter table Tb_Sfac_FPOProfile_Training_Info_Detail
add column TR_COND_FOR varchar(100) null default null,
add column TR_CATEGORY BigInt(20) null default null,
add column TR_VENUE_1 BigInt(20) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231121152216__AL_Tb_Sfac_FPOProfile_Training_Info_Detail_211120231.sql
alter table Tb_Sfac_FPOProfile_Training_Info_Detail_Hist
add column TR_COND_FOR varchar(100) null default null,
add column TR_CATEGORY BigInt(20) null default null,
add column TR_VENUE_1 BigInt(20) null default null;