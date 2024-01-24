--liquibase formatted sql
--changeset Kanchan:V20210907140220__AL_TB_AST_REQUISITION_07092021.sql
alter table TB_AST_REQUISITION modify column AST_NAME VARCHAR(200) NOT NULL;
