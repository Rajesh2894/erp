--liquibase formatted sql
--changeset Anil:V20200520180233__AL_tb_wms_workdefination_20052020.sql
ALTER TABLE tb_wms_workdefination CHANGE COLUMN LOC_ID_EN LOC_ID_EN BIGINT(12) NULL COMMENT 'End Location Id' ;
--liquibase formatted sql
--changeset Anil:V20200520180233__AL_tb_wms_workdefination_200520201.sql
ALTER TABLE tb_wms_workdefination_hist CHANGE COLUMN LOC_ID_EN LOC_ID_EN BIGINT(12) NULL COMMENT 'End Location Id' ;
