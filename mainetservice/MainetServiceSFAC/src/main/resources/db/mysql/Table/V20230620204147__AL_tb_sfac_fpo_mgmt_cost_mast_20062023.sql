--liquibase formatted sql
--changeset Kanchan:V20230620204147__AL_tb_sfac_fpo_mgmt_cost_mast_20062023.sql
Alter table tb_sfac_fpo_mgmt_cost_mast
add column AUTHORIZE_CAPITAL bigint(20) Null default null,
add column  PAID_UP_CAPITAL bigint(20) Null default null,
add column AMT_OF_EQUITY_GRANT_SOUGHT decimal(15,2) Null default null;