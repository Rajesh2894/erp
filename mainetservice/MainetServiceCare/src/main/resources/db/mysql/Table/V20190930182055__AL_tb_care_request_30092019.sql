--liquibase formatted sql
--changeset Anil:V20190930182055__AL_tb_care_request_30092019.sql
ALTER TABLE tb_care_request 
CHANGE COLUMN COMPLAINT_NO COMPLAINT_NO VARCHAR(25) NULL DEFAULT NULL COMMENT 'Complaint No.',
ADD COLUMN CARE_WARD_NO VARCHAR(4) NULL AFTER EXT_REFERENCE_NO;
--liquibase formatted sql
--changeset Anil:V20190930182055__AL_tb_care_request_300920191.sql
ALTER TABLE tb_care_request_hist
CHANGE COLUMN COMPLAINT_NO COMPLAINT_NO VARCHAR(25) NULL DEFAULT NULL COMMENT 'Complaint No.',
ADD COLUMN CARE_WARD_NO VARCHAR(4) NULL AFTER EXT_REFERENCE_NO;
--liquibase formatted sql
--changeset Anil:V20190930182055__AL_tb_care_request_300920192.sql
ALTER TABLE tb_care_request 
CHANGE COLUMN CARE_WARD_NO CARE_WARD_NO BIGINT(12) NULL DEFAULT NULL ;
--liquibase formatted sql
--changeset Anil:V20190930182055__AL_tb_care_request_300920193.sql
ALTER TABLE tb_care_request_hist 
CHANGE COLUMN CARE_WARD_NO CARE_WARD_NO BIGINT(12) NULL DEFAULT NULL ;
