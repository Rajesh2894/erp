--liquibase formatted sql
--changeset nilima:V20180620173921__AL_tb_contract_mast_20062018.sql
ALTER TABLE tb_contract_mast 
ADD COLUMN LOA_NO VARCHAR(50) NULL AFTER cont_map_flag;