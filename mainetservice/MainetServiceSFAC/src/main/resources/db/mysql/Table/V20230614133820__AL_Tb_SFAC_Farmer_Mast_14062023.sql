--liquibase formatted sql
--changeset Kanchan:V20230614133820__AL_Tb_SFAC_Farmer_Mast_14062023.sql
alter table Tb_SFAC_Farmer_Mast add column AREA_TYPE varchar(50) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230614133820__AL_Tb_SFAC_Farmer_Mast_140620231.sql
alter table Tb_Sfac_Equity_Grant_Mast add column COE_NAME varchar (50),add COE_DATE_OF_JOIN date default null;
--liquibase formatted sql
--changeset Kanchan:V20230614133820__AL_Tb_SFAC_Farmer_Mast_140620232.sql
alter Table Tb_Sfac_Equity_Grant_Det modify Column DIN_NO varchar (50) null default null ;
