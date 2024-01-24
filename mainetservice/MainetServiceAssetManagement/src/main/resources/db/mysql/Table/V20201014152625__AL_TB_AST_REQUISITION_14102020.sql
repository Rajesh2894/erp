--liquibase formatted sql
--changeset Kanchan:V20201014152625__AL_TB_AST_REQUISITION_14102020.sql
alter table TB_AST_REQUISITION add column STATUS VARCHAR(20) NOT NUll;
--liquibase formatted sql
--changeset Kanchan:V20201014152625__AL_TB_AST_REQUISITION_141020201.sql
alter table TB_AST_ANNUAL_PLAN_MST add column STATUS VARCHAR(20) NOT NUll;
