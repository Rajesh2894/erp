--liquibase formatted sql
--changeset priya:V20180201124146__AL_tb_wms_scheme_mast_31012018.sql
ALTER TABLE tb_wms_scheme_mast
CHANGE COLUMN SCH_ACTIVE SCH_ACTIVE CHAR(1) NULL DEFAULT NULL AFTER SCH_PROJECTED_REVENU;