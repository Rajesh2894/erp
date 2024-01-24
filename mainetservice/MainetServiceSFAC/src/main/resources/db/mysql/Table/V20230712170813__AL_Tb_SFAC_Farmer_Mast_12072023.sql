--liquibase formatted sql
--changeset PramodPatil:V20230712170813__AL_Tb_SFAC_Farmer_Mast_12072023.sql
Alter table Tb_SFAC_Farmer_Mast add column AC_IN_STATUS char(1) null default 'A';