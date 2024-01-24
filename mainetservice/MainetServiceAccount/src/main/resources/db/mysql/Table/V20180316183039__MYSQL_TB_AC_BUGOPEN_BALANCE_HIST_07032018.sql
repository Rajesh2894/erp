--liquibase formatted sql
--changeset shamik:V20180316183039__MYSQL_TB_AC_BUGOPEN_BALANCE_HIST_070320181.sql
DROP TABLE IF EXISTS TB_AC_BUGOPEN_BALANCE_HIST;
--liquibase formatted sql
--changeset shamik:V20180316183039__MYSQL_TB_AC_BUGOPEN_BALANCE_HIST_070320182.sql
CREATE TABLE TB_AC_BUGOPEN_BALANCE_HIST (
  OPN_ID_H BIGINT(12) COMMENT 'Primary Key',
  OPN_ID BIGINT(12)  COMMENT '',
  FA_YEARID BIGINT(12)  COMMENT 'Year id',
  SAC_HEAD_ID BIGINT(12)  COMMENT 'Secondary Master Reference key -- tb_ac_secondaryhead_master',
  FIELD_ID BIGINT(12) COMMENT 'Field Master Reference key  --TB_AC_FIELD_MASTER',
  FUND_ID BIGINT(12) COMMENT 'Fund Master Reference key --TB_AC_FUND_MASTER',
  FUNCTION_ID BIGINT(12) COMMENT 'Function  Master Reference key --TB_AC_FUNCTION_MASTER',
  PAC_HEAD_ID BIGINT(12) COMMENT 'fk_TB_PRIMARYHEAD_MASTER',
  OPENBAL_AMT DECIMAL(15,2)  COMMENT 'Open Balance Amount',
  CPD_ID_DRCR BIGINT(12) ,
  OPNBAL_TYPE CHAR(1)  COMMENT 'Opening Balance Type',
  FINALIZED_FLAG CHAR(1) ,
  ORGID BIGINT(12)  COMMENT 'Organisation id',
  CREATED_BY BIGINT(12)  COMMENT 'User Identity',
  CREATED_DATE DATETIME  COMMENT 'Last Modification Date',
  LG_IP_MAC VARCHAR(100)  COMMENT 'Client Machine?s Login Name | IP Address | Physical Address',
  UPDATED_BY INT(11) COMMENT 'User id who update the data',
  UPDATED_DATE DATETIME COMMENT 'Date on which data is going to update',
  LG_IP_MAC_UPD VARCHAR(100) COMMENT 'Updated Client Machine?s Login Name | IP Address | Physical Address',
  CLO_BAL_AMT DECIMAL(15,2) COMMENT 'Additional number FI04_N1 to be used in future',
  CLO_BAL_TYPE BIGINT(12) COMMENT 'Additional nvarchar2 FI04_V1 to be used in future',
  SUR_DEF_AMT DECIMAL(15,2) COMMENT 'Additional Date FI04_D1 to be used in future',
  H_STATUS CHAR(1) ,
  PRIMARY KEY (OPN_ID_H)
  );