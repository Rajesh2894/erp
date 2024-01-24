--liquibase formatted sql
--changeset priya:V20180215121531__al_tb_wms_sor_mast__14022018.sql
ALTER TABLE tb_wms_sor_mast
CHANGE COLUMN SOR_TODATE SOR_TODATE DATE NULL COMMENT 'Schedule Rate End Period' ;