--liquibase formatted sql
--changeset nilima:V20180620174148__AL_tb_wms_workestimate_mas_hist_20062018.sql
ALTER TABLE tb_wms_workestimate_mas_hist 
ADD COLUMN WORKE_ESTIMATE_NO VARCHAR(50) NULL COMMENT 'Work Estimate number' AFTER WORK_ID,
ADD COLUMN WORKE_QUANTITY_UTL DECIMAL(5,0) NULL COMMENT 'Quantity utilise' AFTER WORKE_QUANTITY,
ADD COLUMN WORKE_AMOUNT_UTL DECIMAL(20,2) NULL COMMENT 'Total (Quantity*Rate)  Utilise' AFTER WORKE_AMOUNT;