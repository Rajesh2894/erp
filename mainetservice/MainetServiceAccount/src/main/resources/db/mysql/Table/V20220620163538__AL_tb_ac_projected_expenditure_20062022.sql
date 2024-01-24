--liquibase formatted sql
--changeset Kanchan:V20220620163538__AL_tb_ac_projected_expenditure_20062022.sql
alter table tb_ac_projected_expenditure add column
CUR_YR_SPAMT	decimal(15,2)null default null,
add column NXT_YR_SPAMT	decimal(15,2) null default null;

