--liquibase formatted sql
--changeset nilima:V20181121201501_TB_RECEIPT_DET1.sql
ALTER TABLE TB_RECEIPT_DET ADD COLUMN `RM_DEMAND` DECIMAL(12,2) DEFAULT NULL COMMENT 'TAX RELATED DEMAND' AFTER TAX_ID;

