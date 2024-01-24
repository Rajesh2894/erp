--liquibase formatted sql
--changeset Anil:V20190725150632__AL_tb_rl_property_mas_25072019.sql
ALTER TABLE tb_rl_property_mas
CHANGE COLUMN PROP_CAPACITY PROP_CAPACITY BIGINT(12) NULL DEFAULT NULL;
