--liquibase formatted sql
--changeset Kanchan:V20220810122345__CR_mm_indent_10082022.sql
CREATE TABLE mm_indent (
  indentid bigint(12) NOT NULL,
  Indentno varchar(20) NOT NULL,
  indentdate datetime NOT NULL,
  indenter bigint(12) NOT NULL,
  reportingmgr bigint(12) DEFAULT NULL,
  beneficiary varchar(150) DEFAULT NULL,
  storeid bigint(12) NOT NULL,
  Deliveryat varchar(250) DEFAULT NULL,
  expecteddate datetime NOT NULL,
  Issuedby bigint(12) DEFAULT NULL,
  IssuedDate datetime DEFAULT NULL,
  Status char(1) NOT NULL,
  ORGID bigint(4) NOT NULL,
  USER_ID bigint(7) NOT NULL,
  LANGID bigint(4) NOT NULL,
  LMODDATE datetime DEFAULT NULL,
  UPDATED_BY bigint(7) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  WF_Flag varchar(10) NOT NULL,
  PRIMARY KEY (indentid),
  KEY FK_KEY_67 (storeid),
  CONSTRAINT FK_KEY_67 FOREIGN KEY (storeid) REFERENCES mm_storemaster (storeid)
) ;