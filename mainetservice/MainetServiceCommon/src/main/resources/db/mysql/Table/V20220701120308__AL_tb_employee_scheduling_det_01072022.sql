--liquibase formatted sql
--changeset Kanchan:V20220701120308__AL_tb_employee_scheduling_det_01072022.sql
alter table tb_employee_scheduling_det modify column ATT_STATUS varchar(5) null default null;