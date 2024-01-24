--liquibase formatted sql
--changeset Kanchan:V20211130164720__AL_tb_contract_mast_30112021.sql
Alter table tb_contract_mast modify COLUMN 
CONT_TND_NO varchar(40),
modify CONT_TND_DATE datetime,
modify  CONT_RSO_NO varchar(40),
modify CONT_RSO_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211130164720__AL_tb_contract_mast_301120211.sql
alter table tb_contract_mast_hist
modify COLUMN CONT_TND_NO varchar(40),
modify CONT_TND_DATE datetime,
modify CONT_RSO_NO varchar(40),
modify CONT_RSO_DATE datetime NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211130164720__AL_tb_contract_mast_301120212.sql
Alter table tb_contract_mast add column CONT_BAL_AMT decimal(15,2),add CONT_INST_SCHED bigint(12) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20211130164720__AL_tb_contract_mast_301120213.sql
Alter table tb_contract_mast_hist add column CONT_BAL_AMT decimal(15,2),add CONT_INST_SCHED bigint(12) NULL DEFAULT NULL;
