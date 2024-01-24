--liquibase formatted sql
--changeset Kanchan:V20230124193651__AL_tb_sfac_fpo_mgmt_cost_doc_det_24012023.sql
Alter table tb_sfac_fpo_mgmt_cost_doc_det add column STATUS VARCHAR(10) Null default null;