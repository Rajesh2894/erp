--liquibase formatted sql
--changeset Kanchan:V20211115203341__AL_tb_contract_mast_15112021.sql
alter table tb_contract_mast add column APPR_APPL_ID bigint(12),     
add APPR_TYPE bigint(12),     
add  APPR_AMT decimal(15,2) DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211115203341__AL_tb_contract_mast_151120211.sql
alter table tb_contract_mast_hist add column APPR_APPL_ID bigint(12),     
add APPR_TYPE bigint(12),     
add  APPR_AMT decimal(15,2) DEFAULT NULL;

