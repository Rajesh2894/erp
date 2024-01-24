--liquibase formatted sql
--changeset shamik:V20180316183043__MYSQL_TB_AC_TENDER_MASTER_HIST_070320181.sql
DROP TABLE IF EXISTS TB_AC_TENDER_MASTER_HIST;
--liquibase formatted sql
--changeset shamik:V20180316183043__MYSQL_TB_AC_TENDER_MASTER_HIST_070320182.sql
CREATE TABLE TB_AC_TENDER_MASTER_HIST (
  TR_TENDER_ID_H BIGINT(12) COMMENT 'Primary key',
  TR_TENDER_ID BIGINT(12)  COMMENT 'Primary key',
  TR_ENTRY_DATE DATETIME  COMMENT 'Entry Date',
  TR_TENDER_NO VARCHAR(40)  COMMENT 'Tender No.',
  TR_TENDER_DATE DATETIME  COMMENT 'Tender Date',
  TR_TYPE_CPD_ID BIGINT(12)  COMMENT 'Tender cpd_id type',
  TR_NAMEOFWORK VARCHAR(1000)  COMMENT 'Name of work',
  TR_EMD_AMT DECIMAL(15,2) ,
  DP_DEPTID BIGINT(12)  COMMENT 'Department Id',
  VM_VENDORID BIGINT(12)  COMMENT 'Vendor Id',
  SPECIALCONDITIONS VARCHAR(1000)  COMMENT 'Special Conditions',
  TR_TENDER_AMOUNT DECIMAL(15,2)  COMMENT 'Tender Amount',
  TR_PROPOSAL_NO VARCHAR(40)  COMMENT 'Proposal No.',
  TR_PROPOSAL_DATE DATETIME  COMMENT 'Proposal Date ',
  ORGID BIGINT(12)  COMMENT 'organisation id',
  CREATED_BY BIGINT(12)  COMMENT 'user id',
  CREATED_DATE DATETIME  COMMENT 'last modification date',
  UPDATED_BY BIGINT(12)  COMMENT 'user id who update the data',
  UPDATED_DATE DATETIME  COMMENT 'date on which data is going to update',
  LG_IP_MAC VARCHAR(100)  COMMENT 'client machine''s login name | ip address | physical address',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'updated client machine’s login name | ip address | physical address',
  AUTH_BY BIGINT(12)  COMMENT 'Authorised by (employee id)',
  AUTH_REMARK VARCHAR(200)  COMMENT 'Authorisation Remark',
  AUTH_DATE DATETIME  COMMENT 'Authorisation Date',
  AUTH_STATUS CHAR(1)  COMMENT 'Authorisation',
  VOU_ID BIGINT(12)  COMMENT 'FK_TB_AC_VOUCHER',
  H_STATUS CHAR(1) COMMENT 'Record Status',
  PRIMARY KEY (TR_TENDER_ID_H)
 );