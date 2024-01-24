--liquibase formatted sql
--changeset Kanchan:V20220617194700__AL_TB_AST_REQUISITION_17062022.sql
alter table TB_AST_REQUISITION add column REQUISITION_NUMBER varchar(255) null default null;
