--liquibase formatted sql
--changeset Kanchan:V20220711162947__AL_TB_AST_INFO_MST_11072022.sql
alter table TB_AST_INFO_MST add column AST_AVL_STATUS VARCHAR(20) NULL DEFAULT NULL;