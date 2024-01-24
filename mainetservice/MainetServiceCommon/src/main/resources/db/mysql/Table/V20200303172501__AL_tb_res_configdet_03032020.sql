--liquibase formatted sql
--changeset Anil:V20200303172501__AL_tb_res_configdet_03032020.sql
ALTER TABLE tb_res_configdet ADD COLUMN field_id BIGINT(12) NOT NULL AFTER `UPDATED_DATE`;
--liquibase formatted sql
--changeset Anil:V20200303172501__AL_tb_res_configdet_030320201.sql
ALTER TABLE tb_res_configmaster DROP COLUMN field_id;
