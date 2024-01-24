--liquibase formatted sql
--changeset nilima:V20180818160443__CR_tb_lgl_advocate_mas_17082018.sql
CREATE TABLE tb_lgl_advocate_mas (
  adv_id BIGINT(12) NOT NULL COMMENT 'primary key',
  adv_first_nm VARCHAR(100) NOT NULL COMMENT 'Advocate First Name',
  adv_middle_nm VARCHAR(100) NULL COMMENT 'advocate middle name',
  adv_last_nm VARCHAR(100) NOT NULL COMMENT 'advocate last name',
  adv_gen BIGINT(12) NOT NULL COMMENT 'Gender',
  adv_dob DATE NOT NULL COMMENT 'Date of Birth',
  adv_mobile VARCHAR(30) NOT NULL COMMENT 'Mobile No',
  adv_email VARCHAR(100) NULL COMMENT 'Email Id',
  adv_address VARCHAR(200) NULL COMMENT 'Address',
  adv_panno VARCHAR(10) NOT NULL COMMENT 'Pan number',
  adv_uid VARCHAR(28) NOT NULL COMMENT 'Adhar no.',
  adv_experience BIGINT(3) NOT NULL COMMENT 'Experience',
  adv_appfromdate DATE NOT NULL COMMENT 'Appointment From Date',
  adv_apptodate DATE NOT NULL COMMENT 'Appointment Todate',
  adv_maritalstatus CHAR(1) NULL,
  adv_status CHAR(1) NOT NULL COMMENT 'Active->\'Y\',InActive->\'N\'',
  orgid BIGINT(12) NOT NULL COMMENT 'Organisation',
  created_by BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  created_date DATETIME NOT NULL COMMENT 'record creation date',
  updated_by BIGINT(12) NULL COMMENT 'user id who updated the record',
  updated_date DATETIME NULL COMMENT 'date on which updated the record',
  lg_ip_mac VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (adv_id))
COMMENT = 'Advocate Master';

