--liquibase formatted sql
--changeset Kanchan:V20230706183008__AL_tb_sfac_fpo_bank_det_06072023.sql
alter table tb_sfac_fpo_bank_det modify column ACCOUNT_NO varchar(20) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230706183008__AL_tb_sfac_fpo_bank_det_060720231.sql
alter table tb_sfac_fpo_bank_det_hist modify column ACCOUNT_NO varchar(20) null default null;