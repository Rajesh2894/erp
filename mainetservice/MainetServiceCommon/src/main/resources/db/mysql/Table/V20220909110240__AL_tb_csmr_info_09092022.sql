--liquibase formatted sql
--changeset Kanchan:V20220909110240__AL_tb_csmr_info_09092022.sql
ALTER TABLE tb_csmr_info add column CS_BDISWARDNO varchar(10);
--liquibase formatted sql
--changeset Kanchan:V20220909110240__AL_tb_csmr_info_090920221.sql
ALTER TABLE tb_csmr_info add column CS_DISTRICT bigint(20);