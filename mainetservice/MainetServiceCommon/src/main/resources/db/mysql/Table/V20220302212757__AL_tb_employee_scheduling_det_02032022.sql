--liquibase formatted sql
--changeset Kanchan:V20220302212757__AL_tb_employee_scheduling_det_02032022.sql
alter table tb_employee_scheduling_det modify column OT_HOUR varchar(12) null default null;
