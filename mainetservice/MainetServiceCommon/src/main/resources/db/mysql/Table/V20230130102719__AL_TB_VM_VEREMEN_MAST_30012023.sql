--liquibase formatted sql
--changeset Kanchan:V20230130102719__AL_TB_VM_VEREMEN_MAST_30012023.sql
alter table TB_VM_VEREMEN_MAST
add column VEM_QUOTATIONAMT decimal(15,2) null default null,
add column VEM_QUOTATIONAPPDET VARCHAR(100) null default null;