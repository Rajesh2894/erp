--liquibase formatted sql
--changeset Kanchan:V20230614190539__AL_Tb_SFAC_Farmer_Mast_14062023.sql
alter table Tb_SFAC_Farmer_Mast modify column AREA_TYPE bigint(50) null default null;