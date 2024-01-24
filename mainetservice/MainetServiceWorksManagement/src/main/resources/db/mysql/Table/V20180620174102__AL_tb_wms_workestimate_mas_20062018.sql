--liquibase formatted sql
--changeset nilima:V20180620174102__AL_tb_wms_workestimate_mas_20062018.sql
ALTER TABLE tb_wms_workestimate_mas 
ADD COLUMN WORKE_ESTIMATE_NO VARCHAR(50) NULL COMMENT 'Work Estimate number' AFTER WORK_ID,
ADD COLUMN WORKE_QUANTITY_UTL DECIMAL(5,0) NULL COMMENT 'Quantity utilise' AFTER WORKE_QUANTITY,
ADD COLUMN WORKE_AMOUNT_UTL DECIMAL(20,2) NULL COMMENT 'Total (Quantity*Rate)  Utilise' AFTER WORKE_AMOUNT;

--liquibase formatted sql
--changeset nilima:V20180620174102__AL_tb_wms_workestimate_mas_200620183.sql
ALTER TABLE tb_wms_workestimate_mas
CHANGE COLUMN WORKD_File_Name WORKE_File_Name VARCHAR(500) NULL DEFAULT NULL COMMENT 'when measuremnet upoaded' ;

--liquibase formatted sql
--changeset nilima:V20180620174102__AL_tb_wms_workestimate_mas_200620182.sql
ALTER TABLE tb_wms_workestimate_mas 
CHANGE COLUMN WORKE_AMOUNT WORKE_AMOUNT DECIMAL(20,2) NULL COMMENT 'Total (Quantity*Rate)' ;

--liquibase formatted sql
--changeset nilima:V20180620174102__AL_tb_wms_workestimate_mas_200620181.sql
ALTER TABLE tb_wms_workestimate_mas
CHANGE COLUMN WORKE_QUANTITY WORKE_QUANTITY DECIMAL(5,0) NULL COMMENT 'Quantity' ;
