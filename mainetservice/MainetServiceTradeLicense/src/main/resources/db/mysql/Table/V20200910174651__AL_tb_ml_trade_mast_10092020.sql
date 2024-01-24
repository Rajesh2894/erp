--liquibase formatted sql
--changeset Anil:V20200910174651__AL_tb_ml_trade_mast_10092020.sql
ALTER TABLE tb_ml_trade_mast ADD COLUMN TRD_FRE_NOC VARCHAR(10) NULL;
--liquibase formatted sql
--changeset Anil:V20200910174651__AL_tb_ml_trade_mast_100920201.sql
ALTER TABLE tb_ml_trade_mast_hist ADD COLUMN TRD_FRE_NOC VARCHAR(10) NULL;
