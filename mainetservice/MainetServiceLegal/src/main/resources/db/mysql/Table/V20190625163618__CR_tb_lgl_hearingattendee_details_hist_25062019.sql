--liquibase formatted sql
--changeset Anil:V20190625163618__CR_tb_lgl_hearingattendee_details_hist_25062019.sql
drop table if exists tb_lgl_hearingattendee_details_hist;
--liquibase formatted sql
--changeset Anil:V20190625163618__CR_tb_lgl_hearingattendee_details_hist_250620191.sql
CREATE TABLE tb_lgl_hearingattendee_details_hist (
  HRA_ID_HIS BIGINT(12) NOT NULL,
  HRA_ID BIGINT(12) ,
  CSE_ID BIGINT(12) ,
  HRA_NAME VARCHAR(50) COMMENT 'Hearing Attendee Name',
  HRA_DESIGNATION VARCHAR(50)  COMMENT 'Hearing Attendee Designation',
  HRA_PHONENO VARCHAR(50)  COMMENT 'Hearing Phone No.',
  HRA_EMAILID VARCHAR(50)  COMMENT 'Hearing Email Id',
  H_STATUS CHAR(1) COMMENT 'Status',
  ORGID BIGINT(12)  COMMENT 'organization id',
  CREATED_BY BIGINT(12)  COMMENT 'user id who created the record',
  CREATED_DATE DATETIME  COMMENT 'record creation date',
  UPDATED_BY BIGINT(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME  COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (HRA_ID));
