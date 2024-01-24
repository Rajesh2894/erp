--liquibase formatted sql
--changeset Anil:V20190711133019__CR_tb_swd_rtgs_payment_11072019.sql
drop table if exists tb_swd_rtgs_payment;
--liquibase formatted sql
--changeset Anil:V20190711133019__CR_tb_swd_rtgs_payment_110720191.sql
CREATE TABLE tb_swd_rtgs_payment (
  RTGS_TRANS_ID bigint(12) NOT NULL,
  WORK_ORDER_NUMBER bigint(12) NOT NULL,
  WORK_ORDER_DATE date NOT NULL,
  SDSCH_SER_ID bigint(12) NOT NULL,
  SDSCHE_PAYSCH bigint(12) NOT NULL,
  APM_APPLICATION_ID bigint(12) NOT NULL,
  BENEFICIARY_NUMBER varchar(100) NOT NULL,
  BENEFICIARY_NAME varchar(45) NOT NULL,
  AMOUNT decimal(15,2) NOT NULL,
  BANK_ID varchar(45) NOT NULL,
  SAPI_IFSC_ID varchar(100) NOT NULL,
  SAPI_ACCOUNTID bigint(12) NOT NULL,
  REMARK varchar(200) NOT NULL,
  RTGS_STATU char(2) DEFAULT NULL,
  RTGS_POSTST char(2) DEFAULT NULL,
  RTGS_BILLNO varchar(100) DEFAULT NULL,
  ORGID bigint(12) NOT NULL,
  CREATED_DATE varchar(45) NOT NULL,
  CREATED_BY bigint(12) NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (RTGS_TRANS_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
