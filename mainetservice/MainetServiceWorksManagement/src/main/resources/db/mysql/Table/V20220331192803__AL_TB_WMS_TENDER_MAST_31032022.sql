--liquibase formatted sql
--changeset Kanchan:V20220331192803__AL_TB_WMS_TENDER_MAST_31032022.sql
alter table TB_WMS_TENDER_MAST
add column TND_BANKAMT decimal(15,2), 
add TND_PROVAMT decimal(15,2),
add TND_SECAMT decimal(15,2) NULL DEFAULT NULL;
