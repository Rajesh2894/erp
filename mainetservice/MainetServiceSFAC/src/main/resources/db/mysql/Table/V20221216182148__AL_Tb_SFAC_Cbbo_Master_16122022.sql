--liquibase formatted sql
--changeset Kanchan:V20221216182148__AL_Tb_SFAC_Cbbo_Master_16122022.sql
Alter table Tb_SFAC_Cbbo_Master modify column CBBO_ONBOARDING_YEAR datetime null default null;