--liquibase formatted sql
--changeset Anil:V20190617173804__AL_tb_bill_mas_hist_13062019.sql
drop table if exists tb_bill_mas_hist;
--liquibase formatted sql
--changeset Anil:V20190617173804__AL_tb_bill_mas_hist_130620191.sql
CREATE TABLE tb_bill_mas_hist (
  H_BM_ID bigint(12) NOT NULL COMMENT 'Primary Key' ,
  BM_ID bigint(12) NOT NULL COMMENT 'main id',
  FA_YEARID bigint(12) NOT NULL,
  DP_DEPTID bigint(12) NOT NULL,
  SM_SERVICEID bigint(12) DEFAULT NULL,
  APM_APPLICATION_ID bigint(12) DEFAULT NULL,
  RECEIPT_ID bigint(12) DEFAULT NULL,
  CM_REFNO varchar(50) DEFAULT NULL,
  BM_TYPE char(1) DEFAULT NULL COMMENT 'Bill Type(A->Applicationwise,R->Reference)',
  CASE_NO varchar(50) DEFAULT NULL,
  BM_BILLGENDATE date DEFAULT NULL,
  BM_BILLNO varchar(50) NOT NULL,
  BM_BIILLDATE date NOT NULL,
  BM_AMOUNT decimal(15,2) NOT NULL,
  BM_PAID_AMT decimal(20,2) DEFAULT NULL COMMENT 'Bill Paid Amount',
  BM_POSTINGDATE date DEFAULT NULL COMMENT 'Account Poisting date',
  ORGID bigint(12) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
 BM_ISADJUST char(1) DEFAULT NULL,
  BM_ISREFUND char(1) DEFAULT NULL,
  BM_BILL_DISP_ID bigint(12) DEFAULT NULL,
  H_STATUS varchar(2) DEFAULT NULL COMMENT 'History status',
  PRIMARY KEY (H_BM_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8