--liquibase formatted sql
--changeset Kanchan:V20210301184042__AL_tb_employee_01032021.sql
ALTER TABLE employee ADD COLUMN MOBILE_EXTENSION VARCHAR(5) NULL DEFAULT NULL;
--liquibase formatted sql
--changeset Kanchan:V20210301184042__AL_tb_employee_010320211.sql
ALTER TABLE employee_hist ADD COLUMN MOBILE_EXTENSION VARCHAR(5) NULL DEFAULT NULL ;
