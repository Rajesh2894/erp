--liquibase formatted sql
--changeset nilima:V20180609172332__AL_TB_WMS_TENDER_MAST_04062018.sql
ALTER TABLE TB_WMS_TENDER_MAST
DROP COLUMN TND_VENDER,
DROP COLUMN TND_SEC_AMOUNT;


