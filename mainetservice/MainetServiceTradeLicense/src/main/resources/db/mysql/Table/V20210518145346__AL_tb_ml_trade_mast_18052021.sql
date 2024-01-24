--liquibase formatted sql
--changeset Kanchan:V20210518145346__AL_tb_ml_trade_mast_18052021.sql
alter table tb_ml_trade_mast add column  
LAND_TYPE bigint(20) Null,
Add PM_SURVEY_NUMBER  varchar(25) Null,
Add PM_KHATA_NO varchar(50) Null,
Add PM_PLOT_NO varchar(50) Null,
Add PM_ROAD_TYPE bigint(12) Null,
Add PM_AREA_NAME varchar(100) Null;

--liquibase formatted sql
--changeset Kanchan:V20210518145346__AL_tb_ml_trade_mast_180520211.sql
alter table tb_ml_trade_mast_hist add column  
LAND_TYPE bigint(20) Null,
Add PM_SURVEY_NUMBER  varchar(25) Null,
Add PM_KHATA_NO varchar(50) Null,
Add PM_PLOT_NO varchar(50) Null,
Add PM_ROAD_TYPE bigint(12) Null,
Add PM_AREA_NAME varchar(100) Null;

