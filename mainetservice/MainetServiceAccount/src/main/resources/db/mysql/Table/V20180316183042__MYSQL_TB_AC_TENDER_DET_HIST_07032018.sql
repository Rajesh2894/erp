--liquibase formatted sql
--changeset shamik:V20180316183042__MYSQL_TB_AC_TENDER_DET_HIST_070320181.sql
DROP TABLE IF EXISTS TB_AC_TENDER_DET_HIST;
--liquibase formatted sql
--changeset shamik:V20180316183042__MYSQL_TB_AC_TENDER_DET_HIST_070320182.sql
CREATE TABLE TB_AC_TENDER_DET_HIST (
  TR_TENDERID_DET_H BIGINT(12) COMMENT 'Primary Key',
  TR_TENDERID_DET BIGINT(12)  COMMENT '',
  TR_TENDER_ID BIGINT(12)  COMMENT 'Tender Id',
  BUDGETARY_PROV DECIMAL(15,2) ,
  BALANCE_PROV DECIMAL(15,2) ,
  ORGID BIGINT(12)  COMMENT 'Organisation Id',
  CREATED_BY BIGINT(12)  COMMENT 'user id',
  CREATED_DATE DATETIME  COMMENT 'last modification date',
  UPDATED_BY BIGINT(12)  COMMENT 'user id who update the data',
  UPDATED_DATE DATETIME  COMMENT 'date on which data is going to update',
  LANG_ID BIGINT(12)  COMMENT 'language identity',
  LG_IP_MAC VARCHAR(100)  COMMENT 'client machine''s login name | ip address | physical address',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'updated client machine’s login name | ip address | physical address',
  TR_TENDERDET_AMT DECIMAL(15,2)  COMMENT 'Tender Detail Amount',
  SAC_HEAD_ID BIGINT(12)  COMMENT 'additional nvarchar2 fi04_v1 to be used in future',
  FI04_D1 DATETIME  COMMENT 'additional date fi04_d1 to be used in future',
  FI04_LO1 CHAR(1) CHARACTER SET LATIN1  COMMENT 'additional logical field fi04_lo1 to be used in future',
  BUDGETCODE_ID BIGINT(12)  COMMENT 'fk - tb_ac_budgetcode_mas',
  H_STATUS CHAR(1) COMMENT 'Record Status',
  PRIMARY KEY (TR_TENDERID_DET_H)
) ;
