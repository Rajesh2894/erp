--liquibase formatted sql
--changeset Kanchan:V20230220175131__AL_tb_employee_scheduling_det_20022023.sql
alter table tb_employee_scheduling_det add column REMARKS varchar(500) Null default null;