--liquibase formatted sql
--changeset nilima:V20181212113010__AL_tb_care_request_05122018.sql
ALTER TABLE tb_care_request 
ADD COLUMN EXT_REFERENCE_NO VARCHAR(50) NULL AFTER COMPLAINT_NO;
