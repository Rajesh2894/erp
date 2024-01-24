--liquibase formatted sql
--changeset nilima:V20180619212839__CR_tb_wms_scheme_mast_19062018.sql
ALTER TABLE tb_wms_scheme_mast
ADD COLUMN SCH_FUNDNAME VARCHAR(50) NULL AFTER SCH_FUND;