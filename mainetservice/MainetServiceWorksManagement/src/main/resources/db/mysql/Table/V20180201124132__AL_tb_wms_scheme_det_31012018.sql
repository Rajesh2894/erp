--liquibase formatted sql
--changeset priya:V20180201124132__AL_tb_wms_scheme_det_31012018.sql
ALTER TABLE tb_wms_scheme_det
CHANGE COLUMN SCHD_ACTIVE SCHD_ACTIVE CHAR(1) NULL DEFAULT NULL AFTER SCHD_SHARING_PER;