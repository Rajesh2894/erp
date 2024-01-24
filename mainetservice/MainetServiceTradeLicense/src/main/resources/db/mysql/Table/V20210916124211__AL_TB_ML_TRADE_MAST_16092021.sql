--liquibase formatted sql
--changeset Kanchan:V20210916124211__AL_TB_ML_TRADE_MAST_16092021.sql
alter table TB_ML_TRADE_MAST add column TRD_REN_CYCLE  bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210916124211__AL_TB_ML_TRADE_MAST_160920211.sql
alter table tb_ml_trade_mast_hist add column TRD_REN_CYCLE  bigint(12) NULL;
