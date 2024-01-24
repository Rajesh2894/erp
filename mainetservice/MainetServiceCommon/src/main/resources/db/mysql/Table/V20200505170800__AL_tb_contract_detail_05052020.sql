--liquibase formatted sql
--changeset Anil:V20200505170800__AL_tb_contract_detail_05052020.sql
ALTER TABLE tb_contract_detail ADD COLUMN CONT_PRDUNIT BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200505170800__AL_tb_contract_detail_050520201.sql
ALTER TABLE tb_contract_detail_hist ADD COLUMN CONT_PRDUNIT BIGINT(12) NULL;
