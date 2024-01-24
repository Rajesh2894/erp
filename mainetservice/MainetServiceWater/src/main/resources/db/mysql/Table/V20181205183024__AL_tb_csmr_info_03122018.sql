--liquibase formatted sql
--changeset nilima:V20181205183024__AL_tb_csmr_info_03122018.sql
ALTER TABLE tb_csmr_info
CHANGE COLUMN BPL_NO BPL_NO VARCHAR(50) NULL DEFAULT NULL COMMENT 'BPL No. of Citizen' ;

--liquibase formatted sql
--changeset nilima:V20181205183024__AL_tb_csmr_info_031220181.sql
ALTER TABLE tb_csmr_info_hist
CHANGE COLUMN BPL_NO BPL_NO VARCHAR(50) NULL DEFAULT NULL COMMENT 'BPL No. of Citizen' ;



