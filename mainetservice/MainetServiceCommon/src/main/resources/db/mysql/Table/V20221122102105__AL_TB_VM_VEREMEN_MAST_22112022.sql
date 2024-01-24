--liquibase formatted sql
--changeset Kanchan:V20221122102105__AL_TB_VM_VEREMEN_MAST_22112022.sql
alter table TB_VM_VEREMEN_MAST add column WF_STATUS varchar(12) null default null;