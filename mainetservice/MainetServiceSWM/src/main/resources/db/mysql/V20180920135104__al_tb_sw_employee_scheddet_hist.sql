--liquibase formatted sql
--changeset nilima:V20180920135104__al_tb_sw_employee_scheddet_hist.sql
ALTER TABLE tb_sw_employee_scheddet_hist
CHANGE COLUMN RO_ID BEAT_ID BIGINT(12) NULL DEFAULT NULL ;