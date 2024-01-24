--liquibase formatted sql
--changeset Kanchan:V20230110174836__AL_Tb_Sfac_License_Info_Detail_10012023.sql
Alter table Tb_Sfac_License_Info_Detail ADD COLUMN DCOUMENT_NAME varchar(100) null default null;