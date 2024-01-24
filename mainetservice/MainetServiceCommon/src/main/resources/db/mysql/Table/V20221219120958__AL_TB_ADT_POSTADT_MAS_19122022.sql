--liquibase formatted sql
--changeset Kanchan:V20221219120958__AL_TB_ADT_POSTADT_MAS_19122022.sql
Alter table TB_ADT_POSTADT_MAS add column ategoryId INT(12),add AUDIT_PARA_TOYEAR BIGINT(12),add recAmt DECIMAL(12,2)  Null default null;