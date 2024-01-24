--liquibase formatted sql
--changeset Anil:V20200624180024__AL_tb_ml_trade_mast_24062020.sql
ALTER TABLE tb_ml_trade_mast 
ADD COLUMN TRD_EWARD1 BIGINT(12) NULL,
ADD COLUMN TRD_EWARD2 BIGINT(12) NULL,
ADD COLUMN TRD_EWARD3 BIGINT(12) NULL,
ADD COLUMN TRD_EWARD4 BIGINT(12) NULL,
ADD COLUMN TRD_EWARD5 BIGINT(12) NULL,
ADD COLUMN TRD_OTHCAT BIGINT(12) NULL;
--liquibase formatted sql
--changeset Anil:V20200624180024__AL_tb_ml_trade_mast_240620201.sql
ALTER TABLE tb_ml_trade_mast_hist 
ADD COLUMN TRD_EWARD1 BIGINT(12) NULL,
ADD COLUMN TRD_EWARD2 BIGINT(12) NULL,
ADD COLUMN TRD_EWARD3 BIGINT(12) NULL,
ADD COLUMN TRD_EWARD4 BIGINT(12) NULL,
ADD COLUMN TRD_EWARD5 BIGINT(12) NULL,
ADD COLUMN TRD_OTHCAT BIGINT(12) NULL;
