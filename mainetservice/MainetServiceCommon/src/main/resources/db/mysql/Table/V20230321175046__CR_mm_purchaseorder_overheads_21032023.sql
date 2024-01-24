--liquibase formatted sql
--changeset Kanchan:V20230321175046__CR_mm_purchaseorder_overheads_21032023.sql
CREATE TABLE mm_purchaseorder_overheads (
  overheadid bigint(12) NOT NULL,
  poid bigint(12) NOT NULL,
  description varchar(100) DEFAULT NULL,
  overheadtype char(1) DEFAULT NULL,
  amount double(12,2) DEFAULT NULL,
  Status char(1) NOT NULL,
  ORGID bigint(4) NOT NULL,
  USER_ID bigint(7) NOT NULL,
  LANGID bigint(4) NOT NULL,
  LMODDATE datetime DEFAULT NULL,
  UPDATED_BY bigint(7) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (overheadid),
  KEY FK_KEY_16 (poid),
  CONSTRAINT FK_KEY_16 FOREIGN KEY (poid) REFERENCES mm_purchaseorder (poid)
) ;