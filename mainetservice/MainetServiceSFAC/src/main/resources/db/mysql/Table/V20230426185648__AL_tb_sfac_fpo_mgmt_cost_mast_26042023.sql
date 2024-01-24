--liquibase formatted sql
--changeset Kanchan:V20230426185648__AL_tb_sfac_fpo_mgmt_cost_mast_26042023.sql
Alter table tb_sfac_fpo_mgmt_cost_mast
add column APP_BY_IA varchar(20) Null default null,
add column APP_BY_CBBO varchar(20) Null default null;