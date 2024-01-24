--liquibase formatted sql
--changeset Kanchan:V20221110193921__AL_TB_WMS_TENDER_WORK_10112022.sql
alter table TB_WMS_TENDER_WORK add column DISPOSAL_ID BIGINT(12) NULL;