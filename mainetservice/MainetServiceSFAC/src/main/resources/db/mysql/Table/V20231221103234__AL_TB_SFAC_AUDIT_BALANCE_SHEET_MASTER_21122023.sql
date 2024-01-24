--liquibase formatted sql
--changeset PramodPatil:V20231221103234__AL_TB_SFAC_AUDIT_BALANCE_SHEET_MASTER_21122023.sql
alter table TB_SFAC_AUDIT_BALANCE_SHEET_MASTER add column AUDIT_PAR_ID bigint(20) null default null;
