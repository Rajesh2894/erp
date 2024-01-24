--liquibase formatted sql
--changeset nilima:V20171030133659__AL_TB_CARE_REQUEST_220820171.sql
delete from TB_CARE_REQUEST;

--liquibase formatted sql
--changeset nilima:V20171030133659__AL_TB_CARE_REQUEST_2208201711.sql
drop table TB_CARE_REQUEST;

--liquibase formatted sql
--changeset nilima:V20171030133659__AL_TB_CARE_REQUEST_2208201712.sql
CREATE TABLE tb_care_request (
  CARE_REQ_ID bigint(19) NOT NULL COMMENT 'Primary Key',
  REQUEST_TYPE varchar(255) NOT NULL COMMENT 'Request type',
  DEPT_COMP_ID bigint(12) NOT NULL COMMENT 'Depatment Complaint Id',
  COMP_SUBTYPE_ID bigint(12) NOT NULL COMMENT 'complaint Subtype',
  LOC_ID bigint(12) NOT NULL COMMENT 'Location ',
  COMPLAINT_DESC varchar(1020) NOT NULL COMMENT 'Complaint Description',
  APM_APPLICATION_ID bigint(16) NOT NULL,
  CARE_DEP_REF_ID bigint(19) DEFAULT NULL COMMENT 'Care Department Reference Id',
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  PRIMARY KEY (CARE_REQ_ID),
  KEY CARE_REQUEST_FK3 (DEPT_COMP_ID),
  KEY FK_CARE_LOC_ID_idx (LOC_ID),
  KEY FK_CARE_DEP_REF_ID_idx (CARE_DEP_REF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;