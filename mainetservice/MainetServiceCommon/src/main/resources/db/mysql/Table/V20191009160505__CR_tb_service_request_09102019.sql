--liquibase formatted sql
--changeset Anil:V20191009160505__CR_tb_service_request_09102019.sql
drop table if exists tb_service_request;
--liquibase formatted sql
--changeset Anil:V20191009160505__CR_tb_service_request_091020191.sql
CREATE TABLE tb_service_request(
  SERVICE_REQ_ID bigint(19) NOT NULL COMMENT 'Primary Key',
  DATE_OF_REQUEST datetime DEFAULT NULL COMMENT 'Date of request',
  LAST_DATE_OF_ACTION datetime DEFAULT NULL COMMENT 'Last Date of action',
  REQUEST_TYPE varchar(255) NOT NULL COMMENT 'Request type',
  SM_SERVICE_ID bigint(12) NOT NULL COMMENT 'Depatment Complaint Id',
  COMP_SUBTYPE_ID bigint(12) NOT NULL COMMENT 'complaint Subtype',
  LOC_ID bigint(12) NOT NULL COMMENT 'Location ',
  LANDMARK varchar(200) DEFAULT NULL,
  COMPLAINT_DESC varchar(1020) NOT NULL COMMENT 'Complaint Description',
  APM_APPLICATION_ID bigint(16) NOT NULL,
  CARE_DEP_REF_ID bigint(19) DEFAULT NULL COMMENT 'Care Department Reference Id',
  REFERENCE_MODE varchar(500) DEFAULT NULL COMMENT 'Reference Mode',
  REFERENCE_DATE datetime DEFAULT NULL COMMENT 'Reference Date',
  REFERENCE_CATEGORY varchar(500) DEFAULT NULL COMMENT 'Reference category',
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LATITUDE varchar(100) DEFAULT NULL COMMENT 'Latitude',
  LONGITUDE varchar(100) DEFAULT NULL COMMENT 'LONGITUDE',
  COMPLAINT_NO varchar(25) DEFAULT NULL COMMENT 'Complaint No.',
  EXT_REFERENCE_NO varchar(50) DEFAULT NULL,
  CARE_WARD_NO bigint(12) DEFAULT NULL,
  CARE_WARD_NO1 bigint(12) DEFAULT NULL,
  CARE_WARD_NO2 bigint(12) DEFAULT NULL,
  CARE_WARD_NO3 bigint(12) DEFAULT NULL,
  CARE_WARD_NO4 bigint(12) DEFAULT NULL,
  Care_app_type char(1) DEFAULT NULL COMMENT 'Application Type',
  PRIMARY KEY (SERVICE_REQ_ID)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
