--liquibase formatted sql
--changeset Anil:V20191113124523__AL_tb_workflow_det_13112019.sql
alter table tb_workflow_det modify column WFD_SLA decimal(6,2)  DEFAULT NULL COMMENT 'SLA ';
