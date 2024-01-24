--liquibase formatted sql
--changeset Kanchan:V20230130181412__AL_TB_AST_REQUISITION_30012023.sql
alter table TB_AST_REQUISITION modify column ASSET_ID Varchar(500) null default null;