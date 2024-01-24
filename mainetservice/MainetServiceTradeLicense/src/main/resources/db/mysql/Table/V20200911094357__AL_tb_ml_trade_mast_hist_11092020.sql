--liquibase formatted sql
--changeset Anil:V20200911094357__AL_tb_ml_trade_mast_hist_11092020.sql
alter table tb_ml_trade_mast_hist add due_on_property decimal(15,3) default null comment 'amount';
--liquibase formatted sql
--changeset Anil:V20200911094357__AL_tb_ml_trade_mast_hist_110920201.sql
alter table tb_ml_trade_mast_hist add dp_zone bigint(12) default null comment 'drop down id';
--liquibase formatted sql
--changeset Anil:V20200911094357__AL_tb_ml_trade_mast_hist_110920202.sql
alter table tb_ml_trade_mast_hist add building_permission_no bigint(12) default null comment '';
--liquibase formatted sql
--changeset Anil:V20200911094357__AL_tb_ml_trade_mast_hist_110920203.sql
alter table tb_ml_trade_mast_hist add fire_noc_applicable char(1) default null comment 'y or n';
--liquibase formatted sql
--changeset Anil:V20200911094357__AL_tb_ml_trade_mast_hist_110920204.sql
alter table tb_ml_trade_mast_hist add fire_noc_no bigint(12) default null comment '';
