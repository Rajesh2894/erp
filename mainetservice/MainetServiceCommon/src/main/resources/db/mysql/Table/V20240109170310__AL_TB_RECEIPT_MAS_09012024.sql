--liquibase formatted sql
--changeset PramodPatil:V20240109170310__AL_TB_RECEIPT_MAS_09012024.sql
ALTER TABLE TB_RECEIPT_MAS ADD COLUMN gst_No VARCHAR(20) NULL DEFAULT NULL;
