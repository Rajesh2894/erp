--liquibase formatted sql
--changeset Kanchan:V20230316122443__AL_TB_AST_PURCHASER_16032023.sql
alter table TB_AST_PURCHASER modify column PURCHASE_COST decimal(20,2) not null;