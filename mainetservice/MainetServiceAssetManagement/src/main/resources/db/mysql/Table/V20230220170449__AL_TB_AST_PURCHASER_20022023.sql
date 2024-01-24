--liquibase formatted sql
--changeset Kanchan:V20230220170449__AL_TB_AST_PURCHASER_20022023.sql
alter table TB_AST_PURCHASER add column PURCHASE_COST decimal(20,2) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20230220170449__AL_TB_AST_PURCHASER_200220231.sql
alter table TB_AST_PURCHASER_HIST add column PURCHASE_COST decimal(20,2) NOT NULL;