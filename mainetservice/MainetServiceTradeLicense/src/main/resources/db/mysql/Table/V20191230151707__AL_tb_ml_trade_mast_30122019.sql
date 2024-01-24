--liquibase formatted sql
--changeset Anil:V20191230151707__AL_tb_ml_trade_mast_30122019.sql
ALTER TABLE tb_ml_trade_mast
ADD COLUMN agreement_from_date DATE NULL AFTER TRD_FLAT_NO,
ADD COLUMN agreement_to_date DATE NULL AFTER agreement_from_date;
--liquibase formatted sql
--changeset Anil:V20191230151707__AL_tb_ml_trade_mast_301220191.sql
ALTER TABLE tb_ml_trade_mast_hist
ADD COLUMN agreement_from_date DATE NULL AFTER TRD_FLAT_NO,
ADD COLUMN agreement_to_date DATE NULL AFTER agreement_from_date;

