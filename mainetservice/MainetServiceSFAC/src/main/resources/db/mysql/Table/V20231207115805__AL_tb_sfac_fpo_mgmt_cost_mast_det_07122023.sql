--liquibase formatted sql
--changeset PramodPatil:V20231207115805__AL_tb_sfac_fpo_mgmt_cost_mast_det_07122023.sql
alter table tb_sfac_fpo_mgmt_cost_mast_det add column PROPOSED_AMT decimal(15,2) null;
