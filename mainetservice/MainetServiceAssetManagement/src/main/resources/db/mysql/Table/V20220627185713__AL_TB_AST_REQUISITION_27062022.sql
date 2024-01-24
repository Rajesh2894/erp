--liquibase formatted sql
--changeset Kanchan:V20220627185713__AL_TB_AST_REQUISITION_27062022.sql
alter table TB_AST_REQUISITION add column DISPATCH_QTY bigint(12) null default null;