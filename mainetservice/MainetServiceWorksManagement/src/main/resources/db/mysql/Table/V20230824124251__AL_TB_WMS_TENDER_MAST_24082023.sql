--liquibase formatted sql
--changeset PramodPatil:V20230824124251__AL_TB_WMS_TENDER_MAST_24082023.sql
alter table TB_WMS_TENDER_MAST add column WORK_DEV_PER Decimal (15,2) null default '0.00';
