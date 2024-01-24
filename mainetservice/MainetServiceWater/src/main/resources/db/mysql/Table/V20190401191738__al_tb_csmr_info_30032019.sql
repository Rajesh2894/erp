--liquibase formatted sql
--changeset nilima:V20190401191738__al_tb_csmr_info_30032019.sql
ALTER TABLE tb_csmr_info
CHANGE COLUMN COD_DWZID1 COD_DWZID1 BIGINT(12) NOT NULL COMMENT 'Hierarchy for Ward, Zone' ;










