--liquibase formatted sql
--changeset Kanchan:V20221123174036__AL_Tb_SFAC_Fpo_Master_23112022.sql
Alter table  Tb_SFAC_Fpo_Master add column IA_ONBOARDING_YEAR bigint(20),add CBBO_ONBOARDING_YEAR bigint(20) DEFAULT NULL;