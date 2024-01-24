--liquibase formatted sql
--changeset nilima:V20180918195836__CR_TB_LGL_JUDGE_MAST_HIST_18092018.sql
CREATE TABLE TB_LGL_JUDGE_MAST_HIST (
  JUDGE_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  JUDGE_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  JUDGE_FNAME varchar(100) DEFAULT NULL COMMENT 'Judge first name',
  JUDGE_LNAME varchar(100) DEFAULT NULL,
  JUDGE_GENDER bigint(12) DEFAULT NULL COMMENT 'Gender',
  JUDGE_DOB date DEFAULT NULL COMMENT 'Date of Birth',
  JUDGE_CONTACT_NO varchar(50) DEFAULT NULL COMMENT 'Contact No',
  JUDGE_EMAIL_ID varchar(200) DEFAULT NULL COMMENT 'Email Id',
  JUDGE_PANNO varchar(10) DEFAULT NULL COMMENT 'Pan no',
  JUDGE_ADHARNO varchar(15) DEFAULT NULL COMMENT 'Aadhar Number',
  JUDGE_ADDRESS varchar(100) DEFAULT NULL COMMENT 'Address',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (JUDGE_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Judge Master History';
