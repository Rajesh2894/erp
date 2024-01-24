--liquibase formatted sql
--changeset nilima:V20190117125815__CR_TB_CUSTMAST_HIST_17012019.sql
CREATE TABLE TB_CUSTMAST_HIST (
  CM_CUSTID_H BIGINT(12) NOT NULL COMMENT 'Primary Key',
  CM_CUSTID BIGINT(12)  COMMENT 'Primary Key',
  CM_CUSTTYPE BIGINT(12) ,
  CM_CUSTNAME VARCHAR(150)  COMMENT 'Customer Name',
  CM_CUSTADD VARCHAR(200) ,
  CM_EMAIL_ID VARCHAR(100)  COMMENT 'Customer Email Id',
  CM_MOBILE_NO VARCHAR(50)  COMMENT 'Pan No.',
  CM_PAN_NUMBER VARCHAR(20) ,
  CM_UID_NO BIGINT(12)  COMMENT 'UID no.',
  CM_TIN_NUMBER VARCHAR(10)  COMMENT 'taxpayer identification number\nXXX-XX-XXXX',
  CM_TAN_NUMBER VARCHAR(10)  COMMENT 'Tax Deduction and Collection Account Number\n\nFORMAT AAAA99999A',
  CM_GSTNO VARCHAR(15)  COMMENT 'GSTNO format\n\n\n',
  CM_STATUS BIGINT(12) ,
  CM_REMARK VARCHAR(200) ,
  ORGID BIGINT(12) ,
  CREATED_BY VARCHAR(45) ,
  CREATED_DATE VARCHAR(45) ,
  LG_IP_MAC VARCHAR(45) ,
  UPDATED_BY VARCHAR(45) ,
  UPDATED_DATE VARCHAR(45) ,
  LG_IP_MAC_UPD VARCHAR(45) ,
  PRIMARY KEY (CM_CUSTID_H));