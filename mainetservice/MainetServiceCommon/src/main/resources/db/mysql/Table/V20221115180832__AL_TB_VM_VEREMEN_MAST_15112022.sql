--liquibase formatted sql
--changeset Kanchan:V20221115180832__AL_TB_VM_VEREMEN_MAST_15112022.sql
alter table TB_VM_VEREMEN_MAST add column VENDOR_ID bigint(12) DEFAULT null;