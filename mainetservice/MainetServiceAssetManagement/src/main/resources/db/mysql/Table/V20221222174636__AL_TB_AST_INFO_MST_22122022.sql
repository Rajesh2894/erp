--liquibase formatted sql
--changeset Kanchan:V20221222174636__AL_TB_AST_INFO_MST_22122022.sql
alter table TB_AST_INFO_MST add column QUANTITY Bigint(12) null default null;