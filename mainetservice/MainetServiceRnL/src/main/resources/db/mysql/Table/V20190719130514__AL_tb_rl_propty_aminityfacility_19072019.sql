--liquibase formatted sql
--changeset Anil:V20190719130514__AL_tb_rl_propty_aminityfacility_19072019.sql
ALTER TABLE tb_rl_propty_aminityfacility 
CHANGE COLUMN PROP_QUANTITY PROP_QUANTITY BIGINT(12) NULL DEFAULT NULL COMMENT 'Property Quantity';
