--liquibase formatted sql
--changeset Kanchan:V20230109131029__AL_Tb_Sfac_License_Info_Detail_09012022.sql
Alter table Tb_Sfac_License_Info_Detail ADD COLUMN LICENSE_NAME varchar(100) NULL DEFAULT NULL;