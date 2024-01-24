--liquibase formatted sql
--changeset Kanchan:V20230614133531__AL_TB_AST_PURCHASER_14062023.sql
alter table TB_AST_PURCHASER add column DEV_PROPOSAL varchar(500) null default null;