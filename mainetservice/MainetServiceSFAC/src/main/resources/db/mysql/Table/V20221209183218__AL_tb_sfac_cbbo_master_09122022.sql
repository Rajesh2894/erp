--liquibase formatted sql
--changeset Kanchan:V20221209183218__AL_tb_sfac_cbbo_master_09122022.sql
alter table tb_sfac_cbbo_master add column  CBBO_ONBOARDING_YEAR bigint(20)  after IA_NAME ;


