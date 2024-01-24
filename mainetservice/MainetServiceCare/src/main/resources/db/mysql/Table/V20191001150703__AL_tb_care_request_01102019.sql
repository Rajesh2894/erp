--liquibase formatted sql
--changeset Anil:V20191001150703__AL_tb_care_request_01102019.sql
ALTER TABLE tb_care_request ADD COLUMN Care_app_type CHAR(1) NULL COMMENT 'Application Type' AFTER CARE_WARD_NO4;
--liquibase formatted sql
--changeset Anil:V20191001150703__AL_tb_care_request_011020191.sql
ALTER TABLE tb_care_request_hist ADD COLUMN Care_app_type CHAR(1) NULL COMMENT 'Application Type' AFTER CARE_WARD_NO4;
