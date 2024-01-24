--liquibase formatted sql
--changeset Anil:V20191010143635__AL_tb_care_request_10102019.sql
ALTER TABLE tb_care_request 
CHANGE COLUMN DEPT_COMP_ID DEPT_COMP_ID BIGINT(12) NULL COMMENT 'Depatment Complaint Id',
ADD COLUMN SM_SERVICE_ID BIGINT(12) NULL AFTER Care_app_type;
--liquibase formatted sql
--changeset Anil:V20191010143635__AL_tb_care_request_101020191.sql
ALTER TABLE tb_care_request DROP INDEX CARE_REQUEST_FK3;
--liquibase formatted sql
--changeset Anil:V20191010143635__AL_tb_care_request_101020192.sql
ALTER TABLE tb_care_request_hist ADD COLUMN SM_SERVICE_ID BIGINT(12) NULL AFTER UPDATED_DATE;

