--liquibase formatted sql
--changeset Kanchan:V20221216103049__AL_Tb_SFAC_Fpo_Master_16122022.sql
Alter table Tb_SFAC_Fpo_Master modify column BASE_LINE_SURVEY bigint(20) null default null;