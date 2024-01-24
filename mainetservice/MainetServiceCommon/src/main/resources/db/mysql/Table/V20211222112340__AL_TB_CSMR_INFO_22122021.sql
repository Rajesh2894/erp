--liquibase formatted sql
--changeset Kanchan:V20211222112340__AL_TB_CSMR_INFO_22122021.sql
alter table TB_CSMR_INFO add column CS_SOURCE_LINE VARCHAR(12) NULL DEFAULT NULL ;
