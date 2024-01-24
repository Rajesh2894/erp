--liquibase formatted sql
--changeset Kanchan:V20230116201714__AL_TB_AST_REQUISITION_16012023.sql
alter table TB_AST_REQUISITION add column ASSET_ID bigint(12) null default null;