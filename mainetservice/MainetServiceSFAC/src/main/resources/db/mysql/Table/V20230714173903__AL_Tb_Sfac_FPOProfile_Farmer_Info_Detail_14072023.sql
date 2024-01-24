--liquibase formatted sql
--changeset PramodPatil:V20230714173903__AL_Tb_Sfac_FPOProfile_Farmer_Info_Detail_14072023.sql
Alter table Tb_Sfac_FPOProfile_Farmer_Info_Detail add column STATUS char(1) Null default 'A';
