--liquibase formatted sql
--changeset shamik:V20180316183041__MYSQL_TB_AC_SECONDARYHEAD_MAS_HIST_070320181.sql
DROP TABLE IF EXISTS TB_AC_SECONDARYHEAD_MAS_HIST;
--liquibase formatted sql
--changeset shamik:V20180316183041__MYSQL_TB_AC_SECONDARYHEAD_MAS_HIST_070320182.sql
CREATE TABLE TB_AC_SECONDARYHEAD_MAS_HIST (
  SAC_HEAD_ID_H BIGINT(12),
  SAC_HEAD_ID BIGINT(12)  COMMENT 'Primary Key',
  CODCOFDET_ID BIGINT(12)  COMMENT 'FK TB_CODINGSTRUCTURE_MAST',
  PAC_HEAD_ID BIGINT(12)  COMMENT 'Primary Account Headcode Id',
  SAC_LED_TYPE BIGINT(12) ,
  VM_VENDORID BIGINT(12)  COMMENT 'Vendor Id',
  BA_ACCOUNTID BIGINT(12)  COMMENT 'Bank Account id',
  SAC_HEAD_CODE VARCHAR(10)  COMMENT 'Secondary Account Head Code',
  SAC_HEAD_DESC VARCHAR(1000)  COMMENT 'Secondary Account Head Code Description',
  ORGID BIGINT(12)  COMMENT 'Organization id',
  CREATED_BY BIGINT(12)  COMMENT 'User id',
  CREATED_DATE DATETIME  COMMENT 'last modification date',
  UPDATED_BY BIGINT(12)  COMMENT 'user id who update the data',
  UPDATED_DATE DATETIME  COMMENT 'date on which data is going to update',
  LG_IP_MAC VARCHAR(100)  COMMENT 'client machine’s login name | ip address | physical address',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'updated client machine’s login name | ip address | physical address',
  FUNCTION_ID BIGINT(15)  COMMENT 'Function Id',
  FUND_ID BIGINT(12)  COMMENT 'Fund Id',
  FIELD_ID BIGINT(12)  COMMENT 'Field Id',
  AC_HEAD_CODE VARCHAR(1000)  COMMENT 'Account Head Code ',
  STATUS_CPD_ID BIGINT(12)  COMMENT 'Cpd_id Status',
  OLD_ALIAS_LEDGER_CODE VARCHAR(100) ,
  H_STATUS CHAR(1) ,
  PRIMARY KEY (SAC_HEAD_ID_H)
);