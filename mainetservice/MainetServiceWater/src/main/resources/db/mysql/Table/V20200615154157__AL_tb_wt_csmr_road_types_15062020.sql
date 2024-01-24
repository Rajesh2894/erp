--liquibase formatted sql
--changeset Anil:V20200615154157__AL_tb_wt_csmr_road_types_15062020.sql
ALTER TABLE tb_wt_csmr_road_types CHANGE COLUMN CRT_ROAD_UNITS CRT_ROAD_UNITS DECIMAL(12,2) NULL DEFAULT NULL ;
--liquibase formatted sql
--changeset Anil:V20200615154157__AL_tb_wt_csmr_road_types_150620201.sql
ALTER TABLE tb_wt_csmr_road_types_hist CHANGE COLUMN CRT_ROAD_UNITS CRT_ROAD_UNITS DECIMAL(12,2) NULL DEFAULT NULL ;
