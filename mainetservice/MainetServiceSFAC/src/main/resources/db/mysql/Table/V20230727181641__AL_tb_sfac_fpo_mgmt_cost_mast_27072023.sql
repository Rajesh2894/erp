--liquibase formatted sql
--changeset PramodPatil:V20230727181641__AL_tb_sfac_fpo_mgmt_cost_mast_27072023.sql
Alter table tb_sfac_fpo_mgmt_cost_mast
add column fm_ref_id varchar(50) default null,
add column fm_amt_app_dt datetime default null,
add column fm_amt_approved decimal(15,2) default null;