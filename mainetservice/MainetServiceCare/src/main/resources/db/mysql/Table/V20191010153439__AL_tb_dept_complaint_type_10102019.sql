--liquibase formatted sql
--changeset Anil:V20191010153439__AL_tb_dept_complaint_type_10102019.sql
ALTER TABLE tb_dept_complaint_type ADD COLUMN SM_SERVICE_ID BIGINT(12) DEFAULT NULL COMMENT 'Depatment service Id';
