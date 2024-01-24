--liquibase formatted sql
--changeset nilima:V20180829203020__CR_TB_LGL_CASE_PDDETAILS_21082018.sql
CREATE TABLE TB_LGL_CASE_PDDETAILS (
  csed_id bigint(12) NOT NULL COMMENT 'Primary key',
  cse_id bigint(12) NOT NULL COMMENT 'fk_TB_LGL_CASE_MAS',
  csed_name varchar(500) COMMENT 'Plaintiff/Defender Name',
  csed_contactno varchar(10) DEFAULT NULL COMMENT 'Plaintiff/Defender Contact no.',
  csed_emailid   varchar(10) DEFAULT NULL COMMENT 'Plaintiff/Defender Email id',
  csed_Address   varchar(200) DEFAULT NULL COMMENT 'Plaintiff/Defender Address',
  csed_flag      char(1) DEFAULT NULL COMMENT 'Plaintiff->P/Defender->D flag',
  csed_status    char(1) DEFAULT NULL COMMENT 'Y->Active N->Inactive',
  orgid bigint(12) DEFAULT NULL COMMENT 'organization id',
  created_by bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  created_date date DEFAULT NULL COMMENT 'record creation date',
  updated_by bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (cse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Legal Plaintiff/Defender Master';

--liquibase formatted sql
--changeset nilima:V20180829203020__CR_TB_LGL_CASE_PDDETAILS_210820181.sql
CREATE TABLE TB_LGL_CASE_PDDETAILS_HIST (
  csed_id_h bigint(12) NOT NULL COMMENT 'Primary key',
  csed_id bigint(12)  COMMENT 'fk Primary key',
  cse_id bigint(12)  COMMENT 'fk_TB_LGL_CASE_MAS',
  csed_name varchar(500) COMMENT 'Plaintiff/Defender Name',
  csed_contactno varchar(10)  COMMENT 'Plaintiff/Defender Contact no.',
  csed_emailid   varchar(10)  COMMENT 'Plaintiff/Defender Email id',
  csed_Address   varchar(200)  COMMENT 'Plaintiff/Defender Address',
  csed_flag      char(1)  COMMENT 'Plaintiff->P/Defender->D flag',
  csed_status    char(1)  COMMENT 'Y->Active N->Inactive',
  H_status CHAR(1) COMMENT 'Status I->Insert,u->update',
  orgid bigint(12)  COMMENT 'organization id',
  created_by bigint(12)  COMMENT 'user id who created the record',
  created_date date  COMMENT 'record creation date',
  updated_by bigint(12)  COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (csed_id_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Legal Plaintiff/Defender Master History ';