--liquibase formatted sql
--changeset nilima:V20181212113024__AL_tb_care_request_hist_05122018.sql
CREATE TABLE tb_care_request_hist (
  CARE_REQ_ID_H bigint(19) NOT NULL COMMENT 'Primary Key',
  CARE_REQ_ID bigint(19)  COMMENT 'Primary Key',
  DATE_OF_REQUEST datetime  COMMENT 'Date of request',
  LAST_DATE_OF_ACTION datetime  COMMENT 'Last Date of action',
  REQUEST_TYPE varchar(255)  COMMENT 'Request type',
  DEPT_COMP_ID bigint(12)  COMMENT 'Depatment Complaint Id',
  COMP_SUBTYPE_ID bigint(12)  COMMENT 'complaint Subtype',
  LOC_ID bigint(12)  COMMENT 'Location ID',
  LATITUDE varchar(100)  COMMENT 'Latitude',
  LONGITUDE varchar(100)  COMMENT 'LONGITUDE',
  LANDMARK varchar(200) ,
  COMPLAINT_DESC varchar(1020)  COMMENT 'Complaint Description',
  APM_APPLICATION_ID bigint(16)  COMMENT 'Application Id',
  COMPLAINT_NO bigint(12)  COMMENT 'Complaint No.',
  EXT_REFERENCE_NO varchar(50) ,
  CARE_DEP_REF_ID bigint(19)  COMMENT 'Care Department Reference Id',
  REFERENCE_MODE varchar(500)  COMMENT 'Reference Mode',
  REFERENCE_DATE datetime  COMMENT 'Reference Date',
  REFERENCE_CATEGORY varchar(500)  COMMENT 'Reference category',
  ORGID bigint(12)  COMMENT 'Organisation Id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT ' record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  PRIMARY KEY (CARE_REQ_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
