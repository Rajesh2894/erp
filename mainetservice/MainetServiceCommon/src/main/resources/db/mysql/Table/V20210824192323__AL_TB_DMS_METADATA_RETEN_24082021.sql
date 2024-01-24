--liquibase formatted sql
--changeset Kanchan:V20210824192323__AL_TB_DMS_METADATA_RETEN_24082021.sql
alter table TB_DMS_METADATA_RETEN  add column DOC_RETENTION_DATE DATETIME NULL;
