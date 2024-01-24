--liquibase formatted sql
--changeset nilima:V20180712184001__AL_TB_OBJECTION_MAST1.sql
CREATE TABLE TB_PG_BANK (
  PG_ID decimal(15,0) NOT NULL COMMENT 'Primary key',
  BANKID bigint(12) DEFAULT NULL COMMENT 'Bank ID',
  MERCHANT_ID varchar(40) DEFAULT NULL COMMENT 'PG Merchant id',
  PG_NAME varchar(400) DEFAULT NULL COMMENT 'PG Name',
  PG_URL varchar(600) DEFAULT NULL COMMENT 'PG URL',
  PG_STATUS char(1) DEFAULT NULL COMMENT 'Status A-Active, I - Inactive',
  BA_ACCOUNTID bigint(12) DEFAULT NULL COMMENT 'Bank Account Id',
  ORGID int(11) NOT NULL COMMENT 'Organization Id',
  CREATED_BY int(11) DEFAULT NULL COMMENT 'User Identity',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'Created Date',
  LANG_ID int(11) DEFAULT NULL COMMENT 'Language Identity',
  UPDATED_BY int(11) DEFAULT NULL COMMENT 'updated by',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'updated Date',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  COMM_N1 decimal(15,0) DEFAULT NULL COMMENT 'Additional number COMM_N1 to be used in future',
  COMM_V1 varchar(200) DEFAULT NULL COMMENT 'Additional nvarchar2 COMM_V1 to be used in future',
  COMM_D1 datetime DEFAULT NULL COMMENT 'Additional Date COMM_D1 to be used in future',
  COMM_LO1 char(1) DEFAULT NULL COMMENT 'Additional Logical field COMM_LO1 to be used in future',
  PRIMARY KEY (PG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;