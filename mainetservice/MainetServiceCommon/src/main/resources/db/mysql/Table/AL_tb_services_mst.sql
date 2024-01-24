--liquibase formatted sql
--changeset nilima:AL_tb_services_mst.sql
ALTER TABLE tb_services_mst
CHANGE COLUMN COM_N2 SM_PROCESSID BIGINT(12) NULL DEFAULT NULL COMMENT 'BPMN Process name' ;
