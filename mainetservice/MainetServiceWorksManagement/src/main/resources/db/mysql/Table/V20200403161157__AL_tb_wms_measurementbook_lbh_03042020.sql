--liquibase formatted sql
--changeset Anil:V20200403161157__AL_tb_wms_measurementbook_lbh_03042020.sql
ALTER TABLE tb_wms_measurementbook_lbh 
CHANGE COLUMN MB_TOTAL MB_TOTAL DECIMAL(12,4) NULL DEFAULT NULL COMMENT 'Total Quantity' ;
--liquibase formatted sql
--changeset Anil:V20200403161157__AL_tb_wms_measurementbook_lbh_030420201.sql
ALTER TABLE tb_wms_measurementbook_lbh_his
CHANGE COLUMN MB_TOTAL MB_TOTAL DECIMAL(12,4) NULL DEFAULT NULL COMMENT 'Total Quantity' ;
