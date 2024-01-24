--liquibase formatted sql
--changeset Anil:V20200115172739__AL_tb_ml_item_detail_15012020.sql
ALTER TABLE tb_ml_item_detail ADD COLUMN TRI_PENALTY DECIMAL(15,2) NULL AFTER TRD_UNIT;
--liquibase formatted sql
--changeset Anil:V20200115172739__AL_tb_ml_item_detail_150120201.sql
ALTER TABLE tb_ml_item_detail_hist ADD COLUMN TRI_PENALTY DECIMAL(15,2) NULL AFTER TRD_UNIT;
