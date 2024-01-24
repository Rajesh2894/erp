--liquibase formatted sql
--changeset Anil:V20190626162509__CR_tb_swd_rtgs_payment_26062019.sql
drop table if exists tb_swd_rtgs_payment;
--liquibase formatted sql
--changeset Anil:V20190626162509__CR_tb_swd_rtgs_payment_260620191.sql
CREATE TABLE tb_swd_rtgs_payment (
  RTGS_TRANS_ID BIGINT(12) NOT NULL,
  WORK_ORDER_NUMBER BIGINT(12) NOT NULL,
  WORK_ORDER_DATE DATE NOT NULL,
  SDSCH_SER_ID BIGINT(12) NOT NULL,
  SDSCHE_PAYSCH BIGINT(12) NOT NULL,
  APM_APPLICATION_ID BIGINT(12) NOT NULL,
  BENEFICIARY_NUMBER VARCHAR(100) NOT NULL,
  BENEFICIARY_NAME VARCHAR(45) NOT NULL,
  AMOUNT DECIMAL(15,2) NOT NULL,
  BANK_ID VARCHAR(45) NOT NULL,
  SAPI_IFSC_ID VARCHAR(100) NOT NULL,
  SAPI_ACCOUNTID BIGINT(12) NOT NULL,
  REMARK VARCHAR(200) NOT NULL,
  RTGS_STATU CHAR(2) NULL,
  RTGS_POSTST CHAR(2) NULL,
  RTGS_BILLNO VARCHAR(100) NULL,
  ORGID BIGINT(12) NOT NULL,
  CREATED_DATE VARCHAR(45) NOT NULL,
  CREATED_BY BIGINT(12) NOT NULL,
  UPDATED_BY BIGINT(12) NULL,
  UPDATED_DATE DATETIME NULL,
  LG_IP_MAC VARCHAR(100) NOT NULL,
  LG_IP_MAC_UPD VARCHAR(100) NULL,
  PRIMARY KEY (RTGS_TRANS_ID));
