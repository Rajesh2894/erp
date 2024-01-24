--liquibase formatted sql
--changeset priya:V20180215121427__AL_tb_care_department_action_13022018.sql
ALTER TABLE tb_care_department_action
CHANGE COLUMN FORWARDTO_EMPID FORWARDTO_EMPID VARCHAR(250) NULL DEFAULT NULL COMMENT 'Forward employee id' ;
