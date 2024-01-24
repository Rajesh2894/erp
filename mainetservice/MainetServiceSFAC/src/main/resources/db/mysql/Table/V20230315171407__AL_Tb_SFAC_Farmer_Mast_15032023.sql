--liquibase formatted sql
--changeset Kanchan:V20230315171407__AL_Tb_SFAC_Farmer_Mast_15032023.sql
Alter table Tb_SFAC_Farmer_Mast add column FRM_PHOTO varchar(200) Null default null;