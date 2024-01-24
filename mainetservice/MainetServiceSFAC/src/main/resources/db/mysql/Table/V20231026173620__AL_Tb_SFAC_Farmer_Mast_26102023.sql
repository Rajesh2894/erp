--liquibase formatted sql
--changeset PramodPatil:V20231026173620__AL_Tb_SFAC_Farmer_Mast_26102023.sql
alter table Tb_SFAC_Farmer_Mast add column INACTIVE_REMARK varchar(100) null default null;