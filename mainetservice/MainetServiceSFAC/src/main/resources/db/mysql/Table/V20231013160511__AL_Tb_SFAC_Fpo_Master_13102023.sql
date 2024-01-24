--liquibase formatted sql
--changeset PramodPatil:V20231013160511__AL_Tb_SFAC_Fpo_Master_13102023.sql
Alter table Tb_SFAC_Fpo_Master add column FOLIO_NO varchar(100) Null default null;
Alter table TB_SFAC_FPO_MASTER_HIST add column FOLIO_NO varchar(100) Null default null;