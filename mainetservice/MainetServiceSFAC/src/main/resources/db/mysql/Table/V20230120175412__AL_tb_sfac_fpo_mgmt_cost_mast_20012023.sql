--liquibase formatted sql
--changeset Kanchan:V20230120175412__AL_tb_sfac_fpo_mgmt_cost_mast_20012023.sql
Alter table tb_sfac_fpo_mgmt_cost_mast  
add column STATUS Varchar(20) null default null,
add column TOTAL_COST_APPROVED decimal(15,2) null default null;