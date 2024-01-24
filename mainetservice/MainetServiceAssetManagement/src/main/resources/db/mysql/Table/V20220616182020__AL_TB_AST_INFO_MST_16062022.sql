--liquibase formatted sql
--changeset Kanchan:V20220616182020__AL_TB_AST_INFO_MST_16062022.sql
alter table TB_AST_INFO_MST
add column STATUS CHAR(1) null default null;
