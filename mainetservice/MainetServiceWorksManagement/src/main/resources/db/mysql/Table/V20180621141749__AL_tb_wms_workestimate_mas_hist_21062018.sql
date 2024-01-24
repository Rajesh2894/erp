--liquibase formatted sql
--changeset nilima:V20180621141749__AL_tb_wms_workestimate_mas_hist_21062018.sql
ALTER TABLE tb_wms_workestimate_mas_hist
CHANGE COLUMN WORKE_ESTIMATE_NO WORKE_ESTIMATE_NO VARCHAR(50) NULL DEFAULT NULL AFTER WORKE_ESTIMATE_TYPE,
ADD COLUMN ME_LENGTH DECIMAL(7,2) NULL AFTER SORD_LABOUR_RATE,
ADD COLUMN ME_BREADTH DECIMAL(7,2) NULL AFTER ME_LENGTH,
ADD COLUMN ME_HEIGHT DECIMAL(7,2) NULL AFTER ME_BREADTH;
