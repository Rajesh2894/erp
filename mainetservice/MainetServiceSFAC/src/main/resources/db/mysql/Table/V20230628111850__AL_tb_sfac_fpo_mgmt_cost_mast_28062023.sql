--liquibase formatted sql
--changeset Kanchan:V20230628111850__AL_tb_sfac_fpo_mgmt_cost_mast_28062023.sql
Alter table tb_sfac_fpo_mgmt_cost_mast
add column DMC_APPROVAL_DATE datetime Null default null,
add column CEO_CHECK_BOX varchar(10) Null default null,
add column CA_CHECK_BOX varchar(10) Null default null,
add column RETAIL_TIE_UP varchar(10) Null default null,
add column STORE_FOR_SALE varchar(10) Null default null;