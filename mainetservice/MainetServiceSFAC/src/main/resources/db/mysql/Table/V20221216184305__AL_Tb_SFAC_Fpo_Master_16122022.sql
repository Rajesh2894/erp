--liquibase formatted sql
--changeset Kanchan:V20221216184305__AL_Tb_SFAC_Fpo_Master_16122022.sql
Alter table Tb_SFAC_Fpo_Master add column DEPTID bigint(20) Null default null;