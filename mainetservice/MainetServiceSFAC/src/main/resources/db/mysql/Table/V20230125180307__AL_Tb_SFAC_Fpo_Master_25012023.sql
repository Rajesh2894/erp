--liquibase formatted sql
--changeset Kanchan:V20230125180307__AL_Tb_SFAC_Fpo_Master_25012023.sql
Alter table Tb_SFAC_Fpo_Master add column  APPROVED char(1) Null default null;