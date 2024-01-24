--liquibase formatted sql
--changeset Anil:V20200914101048__AL_tb_ml_trade_mast_14092020.sql
alter table tb_ml_trade_mast modify building_permission_no varchar(20) default null;
--liquibase formatted sql
--changeset Anil:V20200914101048__AL_tb_ml_trade_mast_140920201.sql
alter table tb_ml_trade_mast_hist modify building_permission_no varchar(20) default null;
--liquibase formatted sql
--changeset Anil:V20200914101048__AL_tb_ml_trade_mast_140920202.sql
alter table tb_ml_trade_mast drop fire_noc_no;
--liquibase formatted sql
--changeset Anil:V20200914101048__AL_tb_ml_trade_mast_140920203.sql
alter table tb_ml_trade_mast_hist drop fire_noc_no;
