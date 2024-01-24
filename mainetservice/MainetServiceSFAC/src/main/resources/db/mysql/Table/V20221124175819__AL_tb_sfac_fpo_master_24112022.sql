--liquibase formatted sql
--changeset Kanchan:V20221124175819__AL_tb_sfac_fpo_master_24112022.sql
alter table tb_sfac_fpo_master change column IA_ONBOARDING_YEAR  IA_ALLOCATION_YEAR bigint(20) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20221124175819__AL_tb_sfac_fpo_master_241120221.sql
alter table tb_sfac_fpo_master change column CBBO_ONBOARDING_YEAR  IA_ALLOCATION_YEAR_CBBO bigint(20) DEFAULT NULL;