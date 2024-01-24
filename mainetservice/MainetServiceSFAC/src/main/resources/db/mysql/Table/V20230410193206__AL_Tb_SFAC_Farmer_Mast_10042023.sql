--liquibase formatted sql
--changeset Kanchan:V20230410193206__AL_Tb_SFAC_Farmer_Mast_10042023.sql
Alter table Tb_SFAC_Farmer_Mast modify column FRM_VOTERCARD_NO varchar(15) null default null;