--liquibase formatted sql
--changeset Anil:V20190717110913__AL_tb_sw_tripsheet_gdet_16072019.sql
ALTER TABLE tb_sw_tripsheet_gdet 
CHANGE COLUMN TRIP_VOLUME TRIP_VOLUME DECIMAL(15,2) NULL DEFAULT NULL COMMENT 'Volume' ;
