--liquibase formatted sql
--changeset nilima:V20181116164224__CR_TB_RECEIPTBOOKLEAF_MAS_22102018.sql
CREATE TABLE TB_RECEIPTBOOKLEAF_MAS (
  RB_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  FA_YEARID bigint(12) NOT NULL COMMENT 'Financiale Year',
  RB_RECEIPT_BNO varchar(12) NOT NULL COMMENT 'Receipt Book No.',
  RB_RECEIPT_FNO varchar(12) NOT NULL COMMENT 'From Receipt No.',
  RB_RECEIPT_TNO varchar(12) NOT NULL COMMENT 'To Receipt No.',
  EMPID bigint(12) NOT NULL COMMENT 'Employee Id',
  RB_LEAVE bigint(12),
  ORGID bigint(12) NOT NULL COMMENT 'Organization Id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'User Identity',
  CREATED_DATE datetime NOT NULL COMMENT 'Last Modification Date',
  UPDATED_BY int(11) DEFAULT NULL COMMENT 'User id who update the data',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'Date on which data is going to update',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'Client Machine¿s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'Updated Client Machine¿s Login Name | IP Address | Physical Address',
  PRIMARY KEY (RB_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table stores checkbook entries in the bank';

--liquibase formatted sql
--changeset nilima:V20181116164224__CR_TB_RECEIPTBOOKLEAF_MAS_221020181.sql
CREATE TABLE TB_RECEIPTBOOKLEAF_MAS_HIS (
  RB_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  RB_ID bigint(12) COMMENT 'Primary Key',
  FA_YEARID bigint(12) COMMENT 'Financiale Year',
  RB_RECEIPT_BNO varchar(12) COMMENT 'Receipt Book No.',
  RB_RECEIPT_FNO varchar(12) COMMENT 'From Receipt No.',
  RB_RECEIPT_TNO varchar(12) COMMENT 'To Receipt No.',
  EMPID bigint(12) COMMENT 'Employee Id',
  RB_LEAVE bigint(12),
  H_STATUS char(1)  COMMENT 'Active->Y,InActive->N',
  ORGID bigint(12) COMMENT 'Organization Id',
  CREATED_BY bigint(12) COMMENT 'User Identity',
  CREATED_DATE datetime  COMMENT 'Last Modification Date',
  UPDATED_BY int(11) COMMENT 'User id who update the data',
  UPDATED_DATE datetime COMMENT 'Date on which data is going to update',
  LG_IP_MAC varchar(100) COMMENT 'Client Machine¿s Login Name | IP Address | Physical Address',
  LG_IP_MAC_UPD varchar(100) COMMENT 'Updated Client Machine¿s Login Name | IP Address | Physical Address',
  PRIMARY KEY (RB_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='This table stores checkbook entries in the bank';