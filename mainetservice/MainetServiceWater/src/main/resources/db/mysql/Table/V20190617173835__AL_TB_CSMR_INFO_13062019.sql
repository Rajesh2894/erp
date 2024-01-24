--liquibase formatted sql
--changeset Anil:V20190617173835__AL_TB_CSMR_INFO_13062019.sql
ALTER TABLE tb_csmr_info
CHANGE COLUMN WT_D1 CS_ILLEGAL_CDATE DATETIME NULL DEFAULT NULL COMMENT 'illegal Connection Date' ;
--liquibase formatted sql
--changeset Anil:V20190617173835__AL_TB_CSMR_INFO_130620191.sql
ALTER TABLE tb_csmr_info_hist
CHANGE COLUMN WT_D1 CS_ILLEGAL_CDATE DATETIME NULL DEFAULT NULL COMMENT 'illegal Connection Date' ;
