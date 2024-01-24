--liquibase formatted sql
--changeset Kanchan:V20220919202012__AL_tb_csmr_info_19092022.sql
ALTER TABLE tb_csmr_info drop column CS_BDISWARDNO;
