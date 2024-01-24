--liquibase formatted sql
--changeset Kanchan:V20210520105044__AL_tb_ml_trade_mast_20052021.sql
alter table tb_ml_trade_mast add column PM_PLOT_AREA  decimal(15,2)  Null;
--liquibase formatted sql
--changeset Kanchan:V20210520105044__AL_tb_ml_trade_mast_200520211.sql
alter table tb_ml_trade_mast_hist add column PM_PLOT_AREA  decimal(15,2)  Null;
