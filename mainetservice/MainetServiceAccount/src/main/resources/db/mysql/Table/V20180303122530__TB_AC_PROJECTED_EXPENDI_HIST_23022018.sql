--liquibase formatted sql
--changeset priya:V20180303122530__TB_AC_PROJECTED_EXPENDI_HIST_23022018.sql
DROP TABLE IF EXISTS TB_AC_PROJECTED_EXPENDI_HIST;
--liquibase formatted sql
--changeset priya:V20180303122530__TB_AC_PROJECTED_EXPENDI_HIST_230220181.sql
CREATE TABLE TB_AC_PROJECTED_EXPENDI_HIST   (
  PR_EXPENDITUREID_HIST_ID BIGINT(12)  ,
  PR_EXPENDITUREID BIGINT(12)   COMMENT '',
  FA_YEARID BIGINT(12)  COMMENT 'YEAR ID',
  DP_DEPTID BIGINT(12)  COMMENT 'DEPARTMENT ID',
  BUDGETCODE_ID BIGINT(12)  COMMENT 'FK - TB_AC_BUDGETCODE_MAS',
  ORGINAL_ESTAMT DECIMAL(15,2) ,
  REVISED_ESTAMT DECIMAL(15,2) ,
  EXPENDITURE_AMT DECIMAL(15,2)  COMMENT 'EXPENDITURE AMOUNT',
  PR_BALANCE_AMT DECIMAL(15,2) ,
  CPD_BUGSUBTYPE_ID BIGINT(12)  COMMENT 'PREFIX ''FTP''',
  PR_EXP_BUDGETCODE VARCHAR(100) ,
  ORGID BIGINT(12)  COMMENT 'ORGANIZATION ID',
  USER_ID INT(11)  COMMENT 'USER ID',
  LANG_ID INT(11)  COMMENT 'LANGUAGE ID',
  LMODDATE DATETIME  COMMENT 'LAST MODIFICATION DATE',
  UPDATED_BY INT(11)  COMMENT 'USER ID WHO UPDATE THE DATA',
  UPDATED_DATE DATETIME  COMMENT 'DATE ON WHICH DATA IS GOING TO UPDATE',
  LG_IP_MAC VARCHAR(100)  COMMENT 'CLIENT MACHINE''S LOGIN NAME | IP ADDRESS | PHYSICAL ADDRESS',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'UPDATED CLIENT MACHINE’S LOGIN NAME | IP ADDRESS | PHYSICAL ADDRESS',
  FI04_N1 DECIMAL(15,0)  COMMENT 'ADDITIONAL NUMBER FI04_N1 TO BE USED IN FUTURE',
  FI04_V1 VARCHAR(200)  COMMENT 'ADDITIONAL NVARCHAR2 FI04_V1 TO BE USED IN FUTURE',
  FI04_D1 DATETIME  COMMENT 'ADDITIONAL DATE FI04_D1 TO BE USED IN FUTURE',
  FI04_LO1 CHAR(1)  COMMENT 'ADDITIONAL LOGICAL FIELD FI04_LO1 TO BE USED IN FUTURE',
  H_STATUS CHAR(1) ,
  PRIMARY KEY (PR_EXPENDITUREID_HIST_ID)
 );