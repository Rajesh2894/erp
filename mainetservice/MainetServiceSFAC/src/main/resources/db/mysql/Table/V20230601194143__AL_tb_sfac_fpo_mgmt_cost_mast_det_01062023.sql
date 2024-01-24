--liquibase formatted sql
--changeset Kanchan:V20230601194143__AL_tb_sfac_fpo_mgmt_cost_mast_det_01062023.sql
Alter table tb_sfac_fpo_mgmt_cost_mast_det
add column INSTALLMENT bigint(20) Null default null,
add column MONTHS bigint(20) Null default null,
add column TOTAL_MGMT_COST_INCURRED decimal(15,2) Null default null;