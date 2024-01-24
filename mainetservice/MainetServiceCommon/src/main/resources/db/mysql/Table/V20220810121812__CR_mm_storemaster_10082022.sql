--liquibase formatted sql
--changeset Kanchan:V20220810121812__CR_mm_storemaster_10082022.sql
CREATE TABLE mm_storemaster (
  storeid bigint(12) NOT NULL,
  storecode varchar(10) NOT NULL,
  storename varchar(100) NOT NULL,
  location bigint(12) NOT NULL,
  address varchar(255) NOT NULL,
  storeincharge bigint(12) NOT NULL,
  status char(1) NOT NULL,
  ORGID bigint(4) NOT NULL,
  USER_ID bigint(7) NOT NULL,
  LANGID bigint(4) NOT NULL,
  LMODDATE datetime DEFAULT NULL,
  UPDATED_BY bigint(7) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (storeid),
  UNIQUE KEY UK_StoreCode (storecode,ORGID)
) ;