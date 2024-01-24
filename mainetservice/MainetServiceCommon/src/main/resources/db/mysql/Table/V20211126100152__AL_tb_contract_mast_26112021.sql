--liquibase formatted sql
--changeset Kanchan:V20211126100152__AL_tb_contract_mast_26112021.sql
alter table tb_contract_mast add column LEAVY_PENALTY_ID bigint(12),add LEAVY_PENALTY_MODE  bigint(12),add LEAVY_PENALTY_AMT decimal(15,2) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211126100152__AL_tb_contract_mast_261120211.sql
alter table tb_contract_mast_hist add column LEAVY_PENALTY_ID bigint(12),add LEAVY_PENALTY_MODE  bigint(12),add LEAVY_PENALTY_AMT decimal(15,2) NULL DEFAULT NULL;
