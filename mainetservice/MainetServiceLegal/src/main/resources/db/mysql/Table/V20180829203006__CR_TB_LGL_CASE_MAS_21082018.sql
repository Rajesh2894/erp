--liquibase formatted sql
--changeset nilima:V20180829203006__CR_TB_LGL_CASE_MAS_21082018.sql
CREATE TABLE tb_lgl_case_mas (
  cse_id bigint(12) NOT NULL COMMENT 'Primary key',
  cse_name varchar(500) NOT NULL COMMENT 'Case Name',
  cse_suit_no varchar(20) NOT NULL COMMENT 'Suit No.',
  cse_refsuit_no varchar(20) DEFAULT NULL COMMENT 'Reference Suit No.',
  cse_deptid bigint(12) NOT NULL COMMENT 'Department Id',
  cse_cat_id bigint(12) NOT NULL COMMENT 'Category Id',
  cse_subcat_id bigint(12) NOT NULL COMMENT 'SubCateogry',
  cse_date date NOT NULL COMMENT 'Case date',
  cse_entry_dt date NOT NULL COMMENT 'Case Entry Date',
  cse_typ_id bigint(12) NOT NULL COMMENT 'Case Type',
  cse_peic_droa bigint(12) NOT NULL COMMENT 'Organisation As',
  crt_id bigint(12) NOT NULL COMMENT 'Court Name',
  loc_id bigint(12) NOT NULL COMMENT 'Location',
  cse_matdet_1 varchar(1000) NOT NULL COMMENT 'Matter Dispute',
  cse_remarks varchar(1000) NOT NULL COMMENT 'Organisation Remark',
  cse_sect_appl varchar(1000) NOT NULL COMMENT 'Section Act Applied',
  cse_case_status_id bigint(12) NOT NULL COMMENT 'Case Status',
  cse_referenceno varchar(30) DEFAULT NULL COMMENT 'Reference no',
  orgid bigint(12) NOT NULL COMMENT 'organization id',
  created_by bigint(12) NOT NULL COMMENT 'user id who created the record',
  created_date date NOT NULL COMMENT 'record creation date',
  updated_by bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (cse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Legal Case Master';
