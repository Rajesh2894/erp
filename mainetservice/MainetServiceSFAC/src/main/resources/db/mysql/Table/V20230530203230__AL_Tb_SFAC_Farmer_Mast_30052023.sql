--liquibase formatted sql
--changeset Kanchan:V20230530203230__AL_Tb_SFAC_Farmer_Mast_30052023.sql
Alter table Tb_SFAC_Farmer_Mast modify column FRM_TYPE bigint(20) Null DEFAULT null;