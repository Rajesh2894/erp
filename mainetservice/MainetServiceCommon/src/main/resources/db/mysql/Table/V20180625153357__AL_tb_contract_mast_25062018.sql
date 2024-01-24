--liquibase formatted sql
--changeset nilima:V20180625153357__AL_tb_contract_mast_25062018.sql
ALTER TABLE tb_contract_mast
ADD COLUMN LOA_NO VARCHAR(50) NULL AFTER cont_map_flag;