--liquibase formatted sql
--changeset Kanchan:V20230414201154__AL_TB_ML_TRADE_MAST_14042023.sql
ALTER TABLE TB_ML_TRADE_MAST modify Trans_Mode_TYPE   VARCHAR(200) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230414201154__AL_TB_ML_TRADE_MAST_140420231.sql
ALTER TABLE tb_ml_trade_mast_hist modify  Trans_Mode_TYPE  VARCHAR(200) null default null;