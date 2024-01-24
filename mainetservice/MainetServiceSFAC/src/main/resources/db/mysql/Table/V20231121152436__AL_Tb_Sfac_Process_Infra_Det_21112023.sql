--liquibase formatted sql
--changeset PramodPatil:V20231121152436__AL_Tb_Sfac_Process_Infra_Det_21112023.sql
alter table Tb_Sfac_Process_Infra_Det
add column FIN_PROC_PROD1 varchar(100) null default null,
add column FIN_PROC_PROD2 varchar(100) null default null,
add column CONS_PACK_UNIT BigInt(20) null default null,
add column PACK_SIZE_AVAIL varchar(100) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231121152436__AL_Tb_Sfac_Process_Infra_Det_211120231.sql
alter table Tb_Sfac_Process_Infra_Det_Hist
add column FIN_PROC_PROD1 varchar(100) null default null,
add column FIN_PROC_PROD2 varchar(100) null default null,
add column CONS_PACK_UNIT BigInt(20) null default null,
add column PACK_SIZE_AVAIL varchar(100) null default null;