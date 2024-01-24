--liquibase formatted sql
--changeset nilima:V20171221124331__al_tb_workflow_det_21122017.sql
ALTER TABLE tb_workflow_det 
CHANGE COLUMN WFD_SLA WFD_SLA INT(3) NULL COMMENT 'SLA ' ,
CHANGE COLUMN WFD_UNIT WFD_UNIT BIGINT(12) NULL COMMENT 'SLA ->Day,month' ;
