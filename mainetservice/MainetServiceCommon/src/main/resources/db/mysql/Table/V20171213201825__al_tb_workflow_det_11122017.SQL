--liquibase formatted sql
--changeset nilima:V20171213201825__al_tb_workflow_det_11122017.SQL
ALTER TABLE tb_workflow_det 
CHANGE COLUMN WFD_UNIT WFD_UNIT BIGINT(12) NULL COMMENT 'SLA ->Day,month' ;

--liquibase formatted sql
--changeset nilima:V20171213201825__al_tb_workflow_det_111220171.SQL
ALTER TABLE tb_workflow_det
CHANGE COLUMN WFD_SLA WFD_SLA INT(3) NULL COMMENT 'SLA ' ;


