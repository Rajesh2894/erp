--liquibase formatted sql
--changeset nilima:V20180918195759__CR_TB_LGL_JUDGE_MAST_18092018.sql
CREATE TABLE TB_LGL_JUDGE_MAST (
  JUDGE_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  JUDGE_FNAME varchar(100) NOT NULL COMMENT 'Judge first name',
  JUDGE_LNAME varchar(100) NOT NULL,
  JUDGE_GENDER bigint(12) NOT NULL COMMENT 'Gender',
  JUDGE_DOB date NOT NULL COMMENT 'Date of Birth',
  JUDGE_CONTACT_NO varchar(50) NOT NULL COMMENT 'Contact No',
  JUDGE_EMAIL_ID varchar(200) DEFAULT NULL COMMENT 'Email Id',
  JUDGE_PANNO varchar(10) DEFAULT NULL COMMENT 'Pan no',
  JUDGE_ADHARNO varchar(15) DEFAULT NULL COMMENT 'Aadhar Number',
  JUDGE_ADDRESS varchar(100) NOT NULL COMMENT 'Address',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (JUDGE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Judge Master';




