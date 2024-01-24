--liquibase formatted sql
--changeset Kanchan:V20230524125347__AL_Tb_SFAC_Farmer_Mast_24052023.sql
Alter table Tb_SFAC_Farmer_Mast
add column INPUT_REQUIREMENT bigint(20) Null DEFAULT null,
add column QUANTITY_REQ bigint(20) Null DEFAULT null,
add column DATE_REQ datetime Null DEFAULT null,
add column DESCRIPTION varchar(1000) Null DEFAULT null;