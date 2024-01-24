--liquibase formatted sql
--changeset Kanchan:V20230105194210__AL_Tb_SFAC_Farmer_Mast_05012023.sql
Alter table Tb_SFAC_Farmer_Mast modify column FRM_LAND_DET bigint(20) null default null;


