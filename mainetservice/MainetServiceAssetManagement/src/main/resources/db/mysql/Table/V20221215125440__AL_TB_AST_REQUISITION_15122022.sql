--liquibase formatted sql
--changeset Kanchan:V20221215125440__AL_TB_AST_REQUISITION_15122022.sql
Alter table TB_AST_REQUISITION add column REJECTED_QTY bigint(12) Null default null;