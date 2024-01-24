--liquibase formatted sql
--changeset PramodPatil:V20230714143302__AL_Tb_Sfac_Credit_Info_Detail_14072023
Alter table Tb_Sfac_Credit_Info_Detail add column CRD_STATUS char(1) Null default 'A';