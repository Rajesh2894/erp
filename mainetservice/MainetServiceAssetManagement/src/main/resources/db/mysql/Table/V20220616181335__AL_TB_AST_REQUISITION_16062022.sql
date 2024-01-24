--liquibase formatted sql
--changeset Kanchan:V20220616181335__AL_TB_AST_REQUISITION_16062022.sql
alter table TB_AST_REQUISITION
add column EMPLOYEE BIGINT(12) null default null,
add column DISPATCHED_DATE date null default null;
--liquibase formatted sql
--changeset Kanchan:V20220616181335__AL_TB_AST_REQUISITION_160620221.sql
alter table TB_AST_REQUISITION
add column ASSET_CODE VARCHAR(50) null default null,
add column SERIAL_NO VARCHAR(50) null default null,
add column TOTAL_ASSET_QUANTITY BIGINT(12) null default null,
add column REMAINING_QUANTITY BIGINT(12) null default null;

