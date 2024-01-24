--liquibase formatted sql
--changeset Kanchan:V20220825124900__AL_TB_VM_VEREMEN_MAST_25082022.sql
Alter table TB_VM_VEREMEN_MAST modify column VEM_READING decimal(22,1) null default null;