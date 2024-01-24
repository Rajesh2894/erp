--liquibase formatted sql
--changeset Kanchan:V20230106131213__AL_TB_AST_CLASSFCTN_06012023.sql
alter table TB_AST_CLASSFCTN modify column DEPARTMENT bigint(12) NULL default NULL;