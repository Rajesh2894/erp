--liquibase formatted sql
--changeset PramodPatil:V20231006134303__AL_TB_WMS_MEASUREMENTBOOK_LBH_06102023.sql
ALTER TABLE TB_WMS_MEASUREMENTBOOK_LBH add column MB_FLAG bigint(12) null default null;