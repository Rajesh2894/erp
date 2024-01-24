--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR1.sql
drop table if exists TB_LGL_ADVOCATE_MAS;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR2.sql
CREATE TABLE TB_LGL_ADVOCATE_MAS (
  adv_id bigint(12) NOT NULL COMMENT 'primary key',
  adv_first_nm varchar(100) NOT NULL COMMENT 'Advocate First Name',
  adv_middle_nm varchar(100) DEFAULT NULL COMMENT 'advocate middle name',
  adv_last_nm varchar(100) NOT NULL COMMENT 'advocate last name',
  adv_gen bigint(12) NOT NULL COMMENT 'Gender',
  adv_dob date NOT NULL COMMENT 'Date of Birth',
  adv_mobile varchar(30) NOT NULL COMMENT 'Mobile No',
  adv_email varchar(100) DEFAULT NULL COMMENT 'Email Id',
  adv_address varchar(200) DEFAULT NULL COMMENT 'Address',
  adv_panno varchar(10) NOT NULL COMMENT 'Pan number',
  adv_uid varchar(28) NOT NULL COMMENT 'Adhar no.',
  adv_experience decimal(6,2) NOT NULL COMMENT 'Experience',
  adv_appfromdate date NOT NULL COMMENT 'Appointment From Date',
  adv_apptodate date NOT NULL COMMENT 'Appointment Todate',
  adv_maritalstatus char(1) DEFAULT NULL,
  adv_feetype bigint(12) DEFAULT NULL COMMENT 'C->Per Case,L->per lawyer',
  adv_feeamt decimal(15,2) DEFAULT NULL COMMENT 'Fee Amount',
  adv_status char(1) NOT NULL COMMENT 'Active->''Y'',InActive->''N''',
  orgid bigint(12) NOT NULL COMMENT 'Organisation',
  created_by bigint(12) NOT NULL COMMENT 'user id who created the record',
  created_date datetime NOT NULL COMMENT 'record creation date',
  updated_by bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  updated_date datetime DEFAULT NULL COMMENT 'date on which updated the record',
  lg_ip_mac varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (adv_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Advocate Master';


--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR3.sql
drop table if exists TB_LGL_ADVOCATE_MAS_HIST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR4.sql
CREATE TABLE TB_LGL_ADVOCATE_MAS_HIST (
  adv_id_h bigint(12) NOT NULL COMMENT 'primary key',
  adv_id bigint(12) COMMENT 'primary key',
  adv_first_nm varchar(100)  COMMENT 'Advocate First Name',
  adv_middle_nm varchar(100)  COMMENT 'advocate middle name',
  adv_last_nm varchar(100)  COMMENT 'advocate last name',
  adv_gen bigint(12)  COMMENT 'Gender',
  adv_dob date  COMMENT 'Date of Birth',
  adv_mobile varchar(30)  COMMENT 'Mobile No',
  adv_email varchar(100)  COMMENT 'Email Id',
  adv_address varchar(200)  COMMENT 'Address',
  adv_panno varchar(10)  COMMENT 'Pan number',
  adv_uid varchar(28)  COMMENT 'Adhar no.',
  adv_experience bigint(3)  COMMENT 'Experience',
  adv_appfromdate date  COMMENT 'Appointment From Date',
  adv_apptodate date  COMMENT 'Appointment Todate',
  adv_maritalstatus char(1) ,
  adv_feetype char(1) ,
  adv_feeamt decimal(15,2) ,
  adv_status char(1)  COMMENT 'Active->Y,InActive->N',
  H_STATUS char(1)  COMMENT 'Active->Y,InActive->N',
  orgid bigint(12)  COMMENT 'Organisation',
  created_by bigint(12)  COMMENT 'user id who created the record',
  created_date datetime  COMMENT 'record creation date',
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  updated_date datetime  COMMENT 'date on which updated the record',
  lg_ip_mac varchar(100)  COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (adv_id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Advocate Master Hist';


--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR13.sql
drop table if exists TB_LGL_COURT_MAST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR14.sql
CREATE TABLE TB_LGL_COURT_MAST (
  CRT_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  CRT_TYPE bigint(12) NOT NULL COMMENT 'Court Type (CTP Prefix)',
  CRT_NAME varchar(200) NOT NULL COMMENT 'Court Name',
  CRT_NAME_REG varchar(200) NOT NULL COMMENT 'Court Name Reg.',
  CRT_START_TIME varchar(20) NOT NULL COMMENT 'Court Start Time',
  CRT_END_TIME varchar(20) NOT NULL COMMENT 'Court End Time',
  CRT_PHONE_NO varchar(50) NOT NULL COMMENT 'Court Phone No.',
  CRT_EMAIL_ID varchar(50) ,
  CRT_ADDRESS varchar(500) NOT NULL,
  CRT_STATUS char(1) NOT NULL COMMENT 'Court Status(Active->''Y'',''N'')',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (CRT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Court Master';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR15.sql
drop table if exists TB_LGL_COURT_MAST_HIST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR16.sql
CREATE TABLE TB_LGL_COURT_MAST_HIST(
  CRT_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  CRT_ID bigint(12)  COMMENT 'Primary Key',
  CRT_TYPE bigint(12)  COMMENT 'Court Type (CTP Prefix)',
  CRT_NAME varchar(200)  COMMENT 'Court Name',
  CRT_NAME_REG varchar(200)  COMMENT 'Court Name Reg.',
  CRT_START_TIME varchar(20) NOT NULL COMMENT 'Court Start Time',
  CRT_END_TIME varchar(20) NOT NULL COMMENT 'Court End Time',
  CRT_PHONE_NO varchar(50)  COMMENT 'Court Phone No.',
  CRT_EMAIL_ID varchar(50) ,
  CRT_ADDRESS varchar(500) ,
  CRT_STATUS char(1)  COMMENT 'Court Status(Active->''Y'',''N'')',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (CRT_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Court Master History';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR7.sql
drop table if exists TB_LGL_JUDGE_MAST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR8.sql
CREATE TABLE TB_LGL_JUDGE_MAST (
  JUDGE_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  JUDGE_FNAME varchar(100) NOT NULL COMMENT 'Judge first name',
  JUDGE_LNAME varchar(100) NOT NULL,
  JUDGE_GENDER bigint(12) NOT NULL COMMENT 'Gender',
  JUDGE_DOB date NOT NULL COMMENT 'Date of Birth',
  JUDGE_CONTACT_NO varchar(50) NOT NULL COMMENT 'Contact No',
  JUDGE_EMAIL_ID varchar(200)  COMMENT 'Email Id',
  JUDGE_PANNO varchar(10)  COMMENT 'Pan no',
  JUDGE_ADHARNO varchar(15)  COMMENT 'Aadhar Number',
  JUDGE_ADDRESS varchar(100) NOT NULL COMMENT 'Address',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (JUDGE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Judge Master';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR5.sql
drop table if exists TB_LGL_JUDGE_DET;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR6.sql
CREATE TABLE TB_LGL_JUDGE_DET(
  judge_det_id bigint(12) NOT NULL COMMENT 'Primary Key',
  judge_id bigint(12) NOT NULL COMMENT 'foregin key TB_LGL_JUDGE_MAST',
  crt_id bigint(12) NOT NULL COMMENT 'Court Detail',
  from_period date NOT NULL COMMENT 'Appointment from ',
  to_period date  COMMENT 'Appointment To',
  judge_status varchar(50) NOT NULL COMMENT 'Appointment Status',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (judge_det_id),
  KEY FK_JUDGEID_idx (judge_id),
  KEY FK_COURTID_idx (crt_id),
  CONSTRAINT FK_COURTID FOREIGN KEY (crt_id) REFERENCES tb_lgl_court_mast (CRT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_JUDGEID FOREIGN KEY (judge_id) REFERENCES tb_lgl_judge_mast (JUDGE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='judge detail';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR9.sql
drop table if exists TB_LGL_JUDGE_MAST_HIST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR10.sql
CREATE TABLE TB_LGL_JUDGE_MAST_HIST(
  JUDGE_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  JUDGE_ID bigint(12)  COMMENT 'Primary Key',
  JUDGE_FNAME varchar(100)  COMMENT 'Judge first name',
  JUDGE_LNAME varchar(100) ,
  JUDGE_GENDER bigint(12)  COMMENT 'Gender',
  JUDGE_DOB date  COMMENT 'Date of Birth',
  JUDGE_CONTACT_NO varchar(50)  COMMENT 'Contact No',
  JUDGE_EMAIL_ID varchar(200)  COMMENT 'Email Id',
  JUDGE_PANNO varchar(10)  COMMENT 'Pan no',
  JUDGE_ADHARNO varchar(15)  COMMENT 'Aadhar Number',
  JUDGE_ADDRESS varchar(100)  COMMENT 'Address',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (JUDGE_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Judge Master History';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR11.sql
drop table if exists TB_LGL_JUDGE_DET_HIST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR12.sql
CREATE TABLE TB_LGL_JUDGE_DET_HIST (
  judge_det_id_H bigint(12) NOT NULL COMMENT 'Primary Key',
  judge_det_id bigint(12)  COMMENT 'Primary Key',
  judge_id bigint(12)  COMMENT 'foregin key TB_LGL_JUDGE_MAST',
  crt_id bigint(12)  COMMENT 'Court Detail',
  from_period date  COMMENT 'Appointment from ',
  to_period date  COMMENT 'Appointment To',
  judge_status varchar(50)  COMMENT 'Appointment Status',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime ,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (judge_det_id_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='judge detail History';


--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR17.sql
drop table if exists TB_LGL_CASE_MAS;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR18.sql
CREATE TABLE TB_LGL_CASE_MAS (
  cse_id bigint(12) NOT NULL COMMENT 'Primary key',
  cse_name varchar(500) NOT NULL COMMENT 'Case Name',
  cse_suit_no varchar(20) NOT NULL COMMENT 'Suit No.',
  cse_refsuit_no varchar(20)  COMMENT 'Reference Suit No.',
  cse_deptid bigint(12) NOT NULL COMMENT 'Department Id',
  cse_cat_id bigint(12) NOT NULL COMMENT 'Category Id',
  cse_subcat_id bigint(12)  COMMENT 'SubCateogry',
  cse_date date NOT NULL COMMENT 'Case date',
  cse_entry_dt date NOT NULL COMMENT 'Case Entry Date',
  cse_typ_id bigint(12) NOT NULL COMMENT 'Case Type',
  cse_peic_droa bigint(12) NOT NULL COMMENT 'Organisation As',
  crt_id bigint(12) NOT NULL COMMENT 'Court Name',
  adv_id bigint(12)  COMMENT 'Case Advocate',
  loc_id bigint(12) NOT NULL COMMENT 'Location',
  cse_matdet_1 varchar(1000) NOT NULL COMMENT 'Matter Dispute',
  cse_remarks varchar(1000) NOT NULL COMMENT 'Organisation Remark',
  cse_sect_appl varchar(1000) NOT NULL COMMENT 'Section Act Applied',
  cse_case_status_id bigint(12) NOT NULL COMMENT 'Case Status',
  cse_referenceno varchar(30)  COMMENT 'Reference no',
  orgid bigint(12) NOT NULL COMMENT 'organization id',
  created_by bigint(12) NOT NULL COMMENT 'user id who created the record',
  created_date datetime NOT NULL COMMENT 'record creation date',
  updated_date datetime ,
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (cse_id),
  KEY FK_CTRID_idx (crt_id),
  KEY FK_ADVID_idx (adv_id),
  CONSTRAINT FK_ADVID FOREIGN KEY (adv_id) REFERENCES tb_lgl_advocate_mas (adv_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_CTRID FOREIGN KEY (crt_id) REFERENCES tb_lgl_court_mast (CRT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Legal Case Master';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR19.sql
drop table if exists TB_LGL_CASE_MAS_HIST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR20.sql
CREATE TABLE TB_LGL_CASE_MAS_HIST (
  cse_id_h bigint(12) NOT NULL COMMENT 'Primary key',
  cse_id bigint(12)  COMMENT 'Primary key',
  cse_name varchar(500)  COMMENT 'Case Name',
  cse_suit_no varchar(20)  COMMENT 'Suit No.',
  cse_refsuit_no varchar(20)  COMMENT 'Reference Suit No.',
  cse_deptid bigint(12)  COMMENT 'Department Id',
  cse_cat_id bigint(12)  COMMENT 'Category Id',
  cse_subcat_id bigint(12)  COMMENT 'SubCateogry',
  cse_date date  COMMENT 'Case date',
  cse_entry_dt date  COMMENT 'Case Entry Date',
  cse_typ_id bigint(12)  COMMENT 'Case Type',
  cse_subtyp_id bigint(12)  COMMENT 'Case Sub Type Id',
  cse_peic_droa bigint(12)  COMMENT 'Organisation As',
  crt_id bigint(12)  COMMENT 'Court Name',
  loc_id bigint(12)  COMMENT 'Location',
  cse_matdet_1 varchar(1000)  COMMENT 'Matter Dispute',
  cse_remarks varchar(1000)  COMMENT 'Organisation Remark',
  cse_sect_appl varchar(1000)  COMMENT 'Section Act Applied',
  cse_case_status_id bigint(12)  COMMENT 'Case Status',
  H_status char(1)  COMMENT 'Status I->Insert,u->update',
  cse_referenceno varchar(30)  COMMENT 'Reference no',
  orgid bigint(12)  COMMENT 'organization id',
  created_by bigint(12)  COMMENT 'user id who created the record',
  created_date datetime  COMMENT 'record creation date',
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  updated_date datetime ,
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (cse_id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Legal Case Master History';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR21.sql
drop table if exists TB_LGL_CASE_PDDETAILS;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR22.sql
CREATE TABLE TB_LGL_CASE_PDDETAILS (
  csed_id bigint(12) NOT NULL COMMENT 'Primary key',
  cse_id bigint(12) NOT NULL COMMENT 'fk_TB_LGL_CASE_MAS',
  csed_name varchar(500)  COMMENT 'Plaintiff/Defender Name',
  csed_contactno varchar(10)  COMMENT 'Plaintiff/Defender Contact no.',
  csed_emailid varchar(100)  COMMENT 'Plaintiff/Defender Email id',
  csed_Address varchar(200)  COMMENT 'Plaintiff/Defender Address',
  csed_flag char(1) NOT NULL COMMENT 'Plaintiff->P/Defender->D flag',
  csed_status char(1) NOT NULL COMMENT 'Y->Active N->Inactive',
  orgid bigint(12) NOT NULL COMMENT 'organization id',
  created_by bigint(12) NOT NULL COMMENT 'user id who created the record',
  created_date datetime NOT NULL COMMENT 'record creation date',
  updated_date datetime ,
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (csed_id),
  KEY FK_CSE_ID_idx (cse_id),
  CONSTRAINT FK_CSE_ID FOREIGN KEY (cse_id) REFERENCES tb_lgl_case_mas (cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Legal Plaintiff/Defender Master';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR23.sql
drop table if exists TB_LGL_CASE_PDDETAILS_HIST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR24.sql
CREATE TABLE TB_LGL_CASE_PDDETAILS_HIST (
  csed_id_h bigint(12) NOT NULL COMMENT 'Primary key',
  csed_id bigint(12)  COMMENT 'fk Primary key',
  cse_id bigint(12)  COMMENT 'fk_TB_LGL_CASE_MAS',
  csed_name varchar(500)  COMMENT 'Plaintiff/Defender Name',
  csed_contactno varchar(10)  COMMENT 'Plaintiff/Defender Contact no.',
  csed_emailid varchar(100)  COMMENT 'Plaintiff/Defender Email id',
  csed_Address varchar(200)  COMMENT 'Plaintiff/Defender Address',
  csed_flag char(1)  COMMENT 'Plaintiff->P/Defender->D flag',
  csed_status char(1)  COMMENT 'Y->Active N->Inactive',
  H_status char(1)  COMMENT 'Status I->Insert,u->update',
  orgid bigint(12)  COMMENT 'organization id',
  created_by bigint(12)  COMMENT 'user id who created the record',
  created_date datetime  COMMENT 'record creation date',
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  updated_date datetime ,
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (csed_id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Legal Plaintiff/Defender Master History ';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR25.sql
CREATE TABLE TB_LGL_CASEJUDGEMENT_DETAIL (
  cjd_id bigint(12) NOT NULL COMMENT 'Primary Key',
  cse_id bigint(12) NOT NULL COMMENT 'Case Id',
  cjd_date date  COMMENT 'Judgement Detail',
  cjd_type bigint(12)  COMMENT 'Judgement Type',
  cjd_details varchar(1000)  COMMENT 'judegement Details',
  cjd_attendee varchar(50)  COMMENT 'judegement Implementation attendy',
  cjd_actiontaken varchar(50)  COMMENT 'judegement Implementation action',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (cjd_id),
  KEY FK_CJDCSEID_idx (cse_id),
  CONSTRAINT FK_CJDCSEID FOREIGN KEY (cse_id) REFERENCES tb_lgl_case_mas (cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR26.sql
CREATE TABLE TB_LGL_CASEJUDGEMENT_DETAIL_HIST (
  cjd_id_h bigint(12) NOT NULL COMMENT 'Primary Key',
  cjd_id bigint(12)  COMMENT 'Primary Key',
  cse_id bigint(12)  COMMENT 'Case Id',
  cjd_date date  COMMENT 'Judgement Detail',
  cjd_type bigint(12)  COMMENT 'Judgement Type',
  cjd_details varchar(1000)  COMMENT 'judegement Details',
  cjd_attendee varchar(50)  COMMENT 'judegement Implementation attendy',
  cjd_actiontaken varchar(50)  COMMENT 'judegement Implementation action',
  h_status char(1)  COMMENT 'Active->Y,InActive->N',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (cjd_id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR27.sql
CREATE TABLE TB_LGL_CASEPARAWISE_REMARK (
  par_id bigint(12) NOT NULL COMMENT 'Primary Key',
  cse_id bigint(12) NOT NULL COMMENT 'Case Id',
  par_pagno varchar(50) NOT NULL COMMENT 'Para Page No.',
  par_sectionno varchar(45)  COMMENT 'Para Section no.',
  par_comment varchar(500) ,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (par_id),
  KEY fk_pacaseid_idx (cse_id),
  CONSTRAINT fk_pacaseid FOREIGN KEY (cse_id) REFERENCES tb_lgl_case_mas (cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR28.sql
CREATE TABLE TB_LGL_CASEPARAWISE_REMARK_HIST (
  par_id_h bigint(12) NOT NULL COMMENT 'Primary Key',
  par_id bigint(12)  COMMENT 'Primary Key of main table',
  cse_id bigint(12)  COMMENT 'Case Id',
  par_pagno varchar(50)  COMMENT 'Para Page No.',
  par_sectionno varchar(45)  COMMENT 'Para Section no.',
  par_comment varchar(500) ,
  h_status char(1)  COMMENT 'Active->Y,InActive->N',
  orgid bigint(12)  COMMENT 'organization id',
  created_by bigint(12) ,
  created_date datetime  COMMENT 'record creation date',
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  updated_date datetime  COMMENT 'date on which updated the record',
  lg_ip_mac varchar(100)  COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (par_id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR29.sql
drop table if exists TB_LGL_HEARING;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR30.sql
CREATE TABLE TB_LGL_HEARING(
  hr_id bigint(12) NOT NULL COMMENT 'Primary Key',
  cse_id bigint(12) NOT NULL COMMENT 'FK tb_lgl_case_mast',
  judge_id bigint(12)  COMMENT 'FK tb_lgl_judge_mast',
  adv_id bigint(12)  COMMENT 'FK tb_lgl_advocate_mast',
  hr_date datetime  COMMENT 'Hearing Date & Time',
  hr_preparation varchar(45)  COMMENT 'Hearing Preparation',
  hr_rating bigint(12)  COMMENT 'rating',
  hr_attendee varchar(50)  COMMENT 'Attendees',
  hr_proceeding varchar(50)  COMMENT 'Hearing Procedding\n',
  hr_status bigint(12) NOT NULL COMMENT 'Hearing Status',
  orgid bigint(12) NOT NULL COMMENT 'organization id',
  created_by bigint(12) NOT NULL COMMENT 'user id who created the record',
  created_date datetime NOT NULL COMMENT 'record creation date',
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  updated_date datetime  COMMENT 'date on which updated the record',
  lg_ip_mac varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (hr_id),
  KEY FK_HECSEID_idx (cse_id),
  KEY FK_HEJUDGEID_idx (judge_id),
  KEY FK_HEADVID_idx (adv_id),
  CONSTRAINT FK_HEADVID FOREIGN KEY (adv_id) REFERENCES tb_lgl_advocate_mas (adv_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_HECSEID FOREIGN KEY (cse_id) REFERENCES tb_lgl_case_mas (cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_HEJUDGEID FOREIGN KEY (judge_id) REFERENCES tb_lgl_judge_mast (JUDGE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hearing';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR31.sql
drop table if exists TB_LGL_HEARING_HIST;
--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR32.sql
CREATE TABLE TB_LGL_HEARING_HIST(
  hr_id_h bigint(12) NOT NULL COMMENT 'Primary Key',
  hr_id bigint(12)  COMMENT 'Primary Key',
  cse_id bigint(12)  COMMENT 'FK tb_lgl_case_mast',
  judge_id varchar(45)  COMMENT 'FK tb_lgl_judge_mast',
  adv_id bigint(12)  COMMENT 'FK tb_lgl_advocate_mast',
  hr_date datetime  COMMENT 'Hearing Date & Time',
  hr_preparation varchar(45)  COMMENT 'Hearing Preparation',
  hr_rating bigint(12)  COMMENT 'rating',
  hr_attendee varchar(50)  COMMENT 'Attendees',
  hr_proceeding varchar(50)  COMMENT 'Hearing Procedding\n',
  hr_status bigint(12)  COMMENT 'Hearing Status',
  h_status char(1)  COMMENT 'Status I->Insert,U->update',
  orgid bigint(12)  COMMENT 'organization id',
  created_by bigint(12)  COMMENT 'user id who created the record',
  created_date datetime  COMMENT 'record creation date',
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  updated_date datetime  COMMENT 'date on which updated the record',
  lg_ip_mac varchar(100)  COMMENT 'machine ip address from where user has created the record',
  lg_ip_mac_upd varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (hr_id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Hearing History';

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR33.sql
CREATE TABLE TB_LGL_LEGALOPINION(
  lo_opinionid bigint(12) NOT NULL,
  cse_id bigint(12) NOT NULL,
  lo_cpdopinionby bigint(10) ,
  adv_id bigint(20) NOT NULL,
  lo_name varchar(50) ,
  lo_date date ,
  lo_opinion varchar(1000) ,
  orgid bigint(12) NOT NULL,
  created_bY bigint(12) NOT NULL,
  created_date datetime NOT NULL,
  updated_by bigint(12) ,
  updated_date datetime ,
  lg_ip_mac varchar(100) ,
  lg_ip_mac_upd varchar(100) ,
  PRIMARY KEY (lo_opinionid),
  KEY fk_op_cseid_idx (cse_id),
  KEY fk_op_advid_idx (adv_id),
  CONSTRAINT fk_op_advid FOREIGN KEY (adv_id) REFERENCES tb_lgl_advocate_mas (adv_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_op_cseid FOREIGN KEY (cse_id) REFERENCES tb_lgl_case_mas (cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR34.sql
CREATE TABLE TB_LGL_LEGALOPINION_HIST(
  lo_opinionid_h bigint(12) NOT NULL,
  lo_opinionid bigint(12) ,
  cse_id bigint(12) ,
  lo_cpdopinionby bigint(10) ,
  adv_id bigint(20) ,
  lo_name varchar(50) ,
  lo_date date ,
  lo_opinion varchar(1000) ,
  h_status char(1)  COMMENT 'Active->Y,InActive->N',
  orgid bigint(12) ,
  created_bY bigint(12) ,
  created_date datetime ,
  updated_by bigint(12) ,
  updated_date datetime ,
  lg_ip_mac varchar(100) ,
  lg_ip_mac_upd varchar(100) ,
  PRIMARY KEY (lo_opinionid_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR35.sql
CREATE TABLE TB_LGL_LEGALREFERENCE(
  lo_referenceid bigint(12) NOT NULL,
  cse_id bigint(12) NOT NULL,
  lo_referencetype bigint(12) ,
  lo_referencedate datetime ,
  lo_referenceno varchar(100) ,
  lo_description varchar(1000) ,
  orgid bigint(12) NOT NULL,
  created_by bigint(12) NOT NULL,
  created_date datetime NOT NULL,
  updated_by bigint(12) ,
  updated_date datetime ,
  lg_ip_mac varchar(100) NOT NULL,
  lg_ip_mac_upd varchar(100) ,
  PRIMARY KEY (lo_referenceid),
  KEY fk_recseid_idx (cse_id),
  CONSTRAINT fk_recseid FOREIGN KEY (cse_id) REFERENCES tb_lgl_case_mas (cse_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--liquibase formatted sql
--changeset nilima:V20181019201227__ALL_LGL_CR36.sql
CREATE TABLE TB_LGL_LEGALREFERENCE_HIST (
  lo_referenceid_h bigint(12) NOT NULL,
  lo_referenceid bigint(12) ,
  cse_id bigint(12) ,
  lo_referencetype bigint(12) ,
  lo_referencedate datetime ,
  lo_referenceno varchar(100) ,
  lo_description varchar(1000) ,
  h_status char(1)  COMMENT 'Active->Y,InActive->N',
  orgid bigint(12) ,
  created_by bigint(12) ,
  created_date datetime ,
  updated_by bigint(12) ,
  updated_date datetime ,
  lg_ip_mac varchar(100) ,
  lg_ip_mac_upd varchar(100) ,
  PRIMARY KEY (lo_referenceid_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
















