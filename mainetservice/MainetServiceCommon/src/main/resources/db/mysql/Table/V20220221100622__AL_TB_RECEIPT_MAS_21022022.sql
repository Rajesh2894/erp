--liquibase formatted sql
--changeset Kanchan:V20220221100622__AL_TB_RECEIPT_MAS_21022022.sql
Alter table  TB_RECEIPT_MAS  add column CFC_COLCNTR_NO varchar(50) NULL DEFAULT NULL,
add column CFC_COUNTER_NO varchar(50) NULL DEFAULT NULL;

