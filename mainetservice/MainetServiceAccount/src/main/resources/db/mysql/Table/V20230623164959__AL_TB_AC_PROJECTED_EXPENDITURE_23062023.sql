--liquibase formatted sql
--changeset Kanchan:V20230623164959__AL_TB_AC_PROJECTED_EXPENDITURE_23062023.sql
alter table TB_AC_PROJECTED_EXPENDITURE modify column BUDGETCODE_ID bigint(12) not null;