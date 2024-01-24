--liquibase formatted sql
--changeset Kanchan:V20230707190636__AL_Tb_SFAC_Fpo_Master_07072023.sql
Alter table Tb_SFAC_Fpo_Master
modify column TOTAL_AREA_COV_KHARIF decimal(15,2) null default null,
modify column TOTAL_AREA_COV_RABI decimal(15,2) null default null;