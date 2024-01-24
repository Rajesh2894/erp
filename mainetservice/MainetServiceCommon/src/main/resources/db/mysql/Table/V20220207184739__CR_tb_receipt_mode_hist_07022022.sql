--liquibase formatted sql
--changeset Kanchan:V20220207184739__CR_tb_receipt_mode_hist_07022022.sql
drop table tb_receipt_mode_hist;
--liquibase formatted sql
--changeset Kanchan:V20220207184739__CR_tb_receipt_mode_hist_070220221.sql
CREATE TABLE tb_receipt_mode_hist (
  rd_modehistid bigint(12) primary Key NOT NULL,
  RD_MODESID bigint(12) NOT NULL,
  RM_RCPTID bigint(12) NOT NULL,
  CPD_FEEMODE bigint(12) NOT NULL,
  RD_CHEQUEDDNO bigint(12) DEFAULT NULL,
  RD_CHEQUEDDDATE datetime DEFAULT NULL,
  RD_DRAWNON varchar(400) DEFAULT NULL,
  BANKID bigint(12) DEFAULT NULL,
  RD_AMOUNT decimal(12,2) NOT NULL,
  RD_V1 varchar(200) DEFAULT NULL,
  RD_V2 varchar(200) DEFAULT NULL,
  RD_V3 varchar(200) DEFAULT NULL,
  RD_V4 varchar(200) DEFAULT NULL,
  RD_V5 varchar(200) DEFAULT NULL,
  RD_N1 decimal(15,0) DEFAULT NULL,
  RD_N2 decimal(15,0) DEFAULT NULL,
  RD_N3 decimal(15,0) DEFAULT NULL,
  RD_N4 decimal(15,0) DEFAULT NULL,
  RD_N5 decimal(15,0) DEFAULT NULL,
  RD_D1 datetime DEFAULT NULL,
  RD_D2 datetime DEFAULT NULL,
  RD_D3 datetime DEFAULT NULL,
  RD_LO1 char(1) DEFAULT NULL,
  RD_LO2 char(1) DEFAULT NULL,
  RD_LO3 char(1) DEFAULT NULL,
  RD_SR_CHK_DIS char(1) DEFAULT NULL,
  RD_OUTSTATION_CHQ varchar(2) DEFAULT NULL,
  RD_SR_CHK_DATE datetime DEFAULT NULL,
  RD_SR_CHK_DIS_CHG bigint(12) DEFAULT NULL,
  RD_DISHONOR_REMARK varchar(200) DEFAULT NULL,
  BA_ACCOUNTID bigint(12) DEFAULT NULL,
  TRAN_REF_NUMBER varchar(44) DEFAULT NULL,
  TRAN_REF_DATE datetime DEFAULT NULL,
  DISCREPANCY_FLAG char(1) DEFAULT NULL,
  DISCREPANCYDETAILS varchar(1000) DEFAULT NULL,
  ORGID bigint(19) NOT NULL,
  CREATED_BY int(11) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(10) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  RD_INS_MICR_CODE bigint(16) DEFAULT NULL,
  RD_CHEQUE_STATUS bigint(12) DEFAULT NULL,
  ISDELETED varchar(1) DEFAULT NULL,
  RD_ACCT_NO varchar(16) DEFAULT NULL,
  BM_IDNO bigint(20) DEFAULT NULL,
  BM_NO varchar(16) DEFAULT NULL,
  RD_CCN_ID bigint(20) DEFAULT NULL,
  SUPP_BILL_ID bigint(20) DEFAULT NULL,
  H_Status varchar(100) NOT NULL
  
);

