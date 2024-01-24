--liquibase formatted sql
--changeset nilima:V20180629200743__CR_TB_SW_FINECHARGE_COL_29062018.sql
CREATE TABLE TB_SW_FINECHARGE_COL (
  FCH_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  FCH_ENTRYDATE DATE NULL COMMENT 'EntryDate',
  FCH_MOBNO VARCHAR(30) NULL COMMENT 'User MobileNo',
  FCH_NAME VARCHAR(1000) NULL COMMENT 'User Name',
  LATTIUDE VARCHAR(100) NULL COMMENT 'Latitude',
  LONGITUDE VARCHAR(100) NULL COMMENT 'Logitude',
  EMPID BIGINT(12) NULL COMMENT 'FK employee',
  FCH_AMOUNT DECIMAL(12,2) NULL COMMENT 'Fine Charge Amount',
  FCH_FLAG CHAR(1) NULL COMMENT 'Fine Charge WaveOut (Y->Yes,N->No)',
  FCH_MANUAL_NO VARCHAR(30) NULL,
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (FCH_ID));
