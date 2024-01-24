--liquibase formatted sql
--changeset Anil:V20191204115847__AL_tb_lgl_judge_det_04122019.sql
ALTER TABLE tb_lgl_judge_det CHANGE COLUMN judge_status judge_status VARCHAR(50) NULL COMMENT 'Appointment Status';
