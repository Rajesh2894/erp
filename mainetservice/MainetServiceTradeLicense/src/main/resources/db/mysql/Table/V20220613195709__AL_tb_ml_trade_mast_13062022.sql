--liquibase formatted sql
--changeset Kanchan:V20220613195709__AL_tb_ml_trade_mast_13062022.sql
Alter table  tb_ml_trade_mast change  column TRD_BUSNM TRD_BUSNM  varchar(500); 
--liquibase formatted sql
--changeset Kanchan:V20220613195709__AL_tb_ml_trade_mast_130620221.sql
Alter table  tb_ml_trade_mast change  column TRD_BUSADD TRD_BUSADD  varchar(500);
