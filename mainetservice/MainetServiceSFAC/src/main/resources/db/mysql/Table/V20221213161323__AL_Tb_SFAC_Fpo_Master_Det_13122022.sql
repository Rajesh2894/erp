--liquibase formatted sql
--changeset Kanchan:V20221213161323__AL_Tb_SFAC_Fpo_Master_Det_13122022.sql
alter table Tb_SFAC_Fpo_Master_Det add column CROP_TYPE  bigint(20),add CROP_SEASON bigint(20) DEFAULT NULL;