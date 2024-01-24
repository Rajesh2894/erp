--liquibase formatted sql
--changeset Anil:V20190530183114__al_tb_lgl_judge_det.sql
ALTER TABLE tb_lgl_judge_det
CHANGE COLUMN from_period from_period DATE NULL COMMENT 'Appointment from ' ;
