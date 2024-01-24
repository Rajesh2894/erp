--liquibase formatted sql
--changeset Anil:V20190625163609__CR_tb_lgl_hearingattendee_details_25062019.sql
drop table if exists tb_lgl_hearingattendee_details;
--liquibase formatted sql
--changeset Anil:V20190625163609__CR_tb_lgl_hearingattendee_details_250620191.sql
CREATE TABLE tb_lgl_hearingattendee_details (
  HRA_ID BIGINT(12) NOT NULL,
  HRA_NAME VARCHAR(50) NULL COMMENT 'Hearing Attendee Name',
  HRA_DESIGNATION VARCHAR(50) NULL COMMENT 'Hearing Attendee Designation',
  HRA_PHONENO VARCHAR(50) NULL COMMENT 'Hearing Phone No.',
  HRA_EMAILID VARCHAR(50) NULL COMMENT 'Hearing Email Id',
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (HRA_ID));

