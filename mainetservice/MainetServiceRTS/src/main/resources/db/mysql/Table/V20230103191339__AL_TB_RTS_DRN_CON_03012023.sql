--liquibase formatted sql
--changeset Kanchan:V20230103191339__AL_TB_RTS_DRN_CON_03012023.sql
alter table TB_RTS_DRN_CON add column APPL_TYPE bigint(12) NULL DEFAULT NULL;