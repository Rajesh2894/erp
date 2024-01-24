--liquibase formatted sql
--changeset nilima:V20181115164814__AL_TB_CONTRACT_MAST_12112018.sql
ALTER TABLE tb_contract_mast 
CHANGE COLUMN CONT_RENEWAL CONT_RENEWAL CHAR(1) NULL COMMENT 'Renewal of Contract (Y,N)' ;
