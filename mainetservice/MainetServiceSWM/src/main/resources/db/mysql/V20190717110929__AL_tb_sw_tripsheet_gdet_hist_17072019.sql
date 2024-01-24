--liquibase formatted sql
--changeset Anil:V20190717110929__AL_tb_sw_tripsheet_gdet_hist_17072019.sql
ALTER TABLE tb_sw_tripsheet_gdet_hist 
CHANGE COLUMN TRIP_VOLUME TRIP_VOLUME DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Volume' ;
