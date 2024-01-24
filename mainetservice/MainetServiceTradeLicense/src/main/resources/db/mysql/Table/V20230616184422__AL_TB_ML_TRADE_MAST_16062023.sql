--liquibase formatted sql
--changeset Kanchan:V20230616184422__AL_TB_ML_TRADE_MAST_16062023.sql
ALTER TABLE TB_ML_TRADE_MAST ADD TRD_BUS_NATURE VARCHAR(300) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230616184422__AL_TB_ML_TRADE_MAST_160620231.sql
ALTER TABLE tb_ml_trade_mast_hist ADD TRD_BUS_NATURE VARCHAR(300) null default null;