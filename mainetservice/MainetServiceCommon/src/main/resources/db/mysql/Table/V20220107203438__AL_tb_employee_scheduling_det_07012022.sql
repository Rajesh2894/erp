--liquibase formatted sql
--changeset Kanchan:V20220107203438__AL_tb_employee_scheduling_det_07012022.sql
alter table tb_employee_scheduling_det add column OT_CPD_SHIFT_ID bigint(12),add ATT_STATUS char(1) DEFAULT NULL;
