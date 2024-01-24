--liquibase formatted sql
--changeset PramodPatil:V20231121152500__AL_Tb_Sfac_Market_Linkage_Info_Detail_21112023.sql
alter table Tb_Sfac_Market_Linkage_Info_Detail
add column MR_DATE_OF_REG datetime null default null,
add column MR_PLAT_NAME BigInt(20) null default null,
add column MR_REG_NO varchar(100) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231121152500__AL_Tb_Sfac_Market_Linkage_Info_Detail_211120231.sql
alter table Tb_Sfac_Market_Linkage_Info_Detail_Hist
add column MR_DATE_OF_REG datetime null default null,
add column MR_PLAT_NAME BigInt(20) null default null,
add column MR_REG_NO varchar(100) null default null;