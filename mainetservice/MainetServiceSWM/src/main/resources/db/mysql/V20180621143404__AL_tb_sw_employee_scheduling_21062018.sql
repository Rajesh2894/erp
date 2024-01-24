--liquibase formatted sql
--changeset nilima:V20180621143404__AL_tb_sw_employee_scheduling_21062018.sql
ALTER TABLE tb_sw_employee_scheduling
CHANGE COLUMN EMS_TYPE EMS_TYPE CHAR(1) NOT NULL COMMENT 'Schedule Type' ;
