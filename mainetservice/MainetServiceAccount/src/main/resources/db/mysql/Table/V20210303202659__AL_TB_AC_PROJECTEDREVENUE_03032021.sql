--liquibase formatted sql
--changeset Kanchan:V20210303202659__AL_TB_AC_PROJECTEDREVENUE_03032021.sql
alter table TB_AC_PROJECTEDREVENUE change column FI04_LO1 FUND_ID  BigInt(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20210303202659__AL_TB_AC_PROJECTEDREVENUE_030320211.sql
alter table TB_AC_PROJECTED_EXPENDITURE change column FI04_LO1 FUND_ID  BigInt(12) null default null;
--liquibase formatted sql
--changeset Kanchan:V20210303202659__AL_TB_AC_PROJECTEDREVENUE_030320212.sql
alter table TB_AC_BUDGETORY_ESTIMATE change column FI04_LO1 FUND_ID  BigInt(12) null default null;
