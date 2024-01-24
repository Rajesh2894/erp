--liquibase formatted sql
--changeset Kanchan:V20230313174110__AL_Tb_SFAC_Farmer_Mast_13032023.sql
Alter table  Tb_SFAC_Farmer_Mast
add column PIN_CODE varchar(10) Null default null,
add column ADDRESS varchar(350) Null default null,
add column DATE_OF_BIRTH datetime Null default null,
add column LAND_OWNED bigint(20) Null default null,
add column LAND_LEASED bigint(20) Null default null;