--liquibase formatted sql
--changeset Anil:V20200327120424__AL_tb_ml_trade_mast_27032020.sql
ALTER TABLE tb_ml_trade_mast ADD COLUMN CAN_REMARK VARCHAR(200) NULL;
--liquibase formatted sql
--changeset Anil:V20200327120424__AL_tb_ml_trade_mast_270320201.sql
ALTER TABLE tb_ml_trade_mast_hist ADD COLUMN CAN_REMARK VARCHAR(200) NULL;
