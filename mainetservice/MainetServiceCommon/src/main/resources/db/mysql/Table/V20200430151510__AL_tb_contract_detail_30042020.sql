--liquibase formatted sql
--changeset Anil:V20200430151510__AL_tb_contract_detail_30042020.sql
ALTER TABLE tb_contract_detail CHANGE COLUMN CONT_DEP_PRTCL CONT_DEP_PRTCL VARCHAR(200) NULL DEFAULT NULL ;
--liquibase formatted sql
--changeset Anil:V20200430151510__AL_tb_contract_detail_300420201.sql
ALTER TABLE tb_contract_detail_hist CHANGE COLUMN CONT_DEP_PRTCL CONT_DEP_PRTCL VARCHAR(200) NULL DEFAULT NULL ;
