--liquibase formatted sql
--changeset PramodPatil:V20230713173723__AL_Tb_Sfac_Financial_Info_Detail_13072023.sql
Alter table Tb_Sfac_Financial_Info_Detail add column FIN_STATUS char(1) Null default 'A';
