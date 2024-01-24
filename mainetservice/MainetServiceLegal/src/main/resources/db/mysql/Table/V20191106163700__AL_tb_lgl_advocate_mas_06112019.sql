--liquibase formatted sql
--changeset Anil:V20191106163700__AL_tb_lgl_advocate_mas_06112019.sql
ALTER TABLE tb_lgl_advocate_mas
CHANGE COLUMN adv_panno adv_panno VARCHAR(10) NULL COMMENT 'Pan number',
CHANGE COLUMN adv_uid adv_uid VARCHAR(28) NULL COMMENT 'Adhar no.',
CHANGE COLUMN adv_experience adv_experience DECIMAL(6,2) NULL COMMENT 'Experience',
CHANGE COLUMN adv_apptodate adv_apptodate DATE NULL COMMENT 'Appointment Todate';
