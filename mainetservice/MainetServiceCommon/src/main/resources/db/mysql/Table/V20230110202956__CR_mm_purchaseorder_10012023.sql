--liquibase formatted sql
--changeset Kanchan:V20230110202956__CR_mm_purchaseorder_10012023.sql
 CREATE TABLE mm_purchaseorder (
  poid bigint(12) NOT NULL,
  pono varchar(20) NOT NULL,
  podate date NOT NULL ,
  storeid bigint(12) NOT NULL,
  workorderid bigint(12) NOT NULL,
  vendorid bigint(12) NOT NULL,
  expecteddeliverydate date NOT NULL,
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
  PRIMARY KEY (poid),
  KEY FK_KEY_11 (storeid),
  CONSTRAINT FK_KEY_11 FOREIGN KEY (storeid) REFERENCES mm_storemaster (storeid)
);
--liquibase formatted sql
--changeset Kanchan:V20230110202956__CR_mm_purchaseorder_100120231.sql
CREATE TABLE mm_requisition (
  prid bigint(12) NOT NULL,
  prno varchar(20) NOT NULL,
  prdate datetime NOT NULL,
  storeid decimal(12,0) NOT NULL,
  requestedby bigint(12) NOT NULL,
  department bigint(12) NOT NULL,
  Status char(1) NOT NULL,
  poref bigint(12) DEFAULT NULL,
  ORGID bigint(4) NOT NULL,
  USER_ID bigint(7) NOT NULL,
  LANGID bigint(4) NOT NULL,
  LMODDATE datetime DEFAULT NULL,
  UPDATED_BY bigint(7) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  WF_Flag varchar(10) NOT NULL,
  PRIMARY KEY (prid),
  KEY FK_KEY_7 (poref),
  CONSTRAINT FK_KEY_7 FOREIGN KEY (poref) REFERENCES mm_purchaseorder (poid)
) ;
--liquibase formatted sql
--changeset Kanchan:V20230110202956__CR_mm_purchaseorder_100120232.sql
CREATE TABLE mm_itemmaster (
  Itemid bigint(12) NOT NULL,
  itemcode varchar(15) NOT NULL,
  name varchar(200) NOT NULL,
  description varchar(255) DEFAULT NULL,
  uom bigint(12) NOT NULL,
  itemgroup bigint(12) NOT NULL,
  itemsubgroup bigint(12) NOT NULL,
  type bigint(12) NOT NULL,
  isasset char(1) DEFAULT NULL,
  classification bigint(12) DEFAULT NULL,
  valuemethod bigint(12) DEFAULT NULL,
  management bigint(12) DEFAULT NULL,
  minlevel bigint(12) DEFAULT NULL,
  reorderlevel bigint(12) DEFAULT NULL,
  category bigint(12) NOT NULL,
  isexpiry char(1) DEFAULT NULL,
  expirytype bigint(12) DEFAULT NULL,
  hsncode bigint(10) DEFAULT NULL,
  taxpercentage decimal(5,2) DEFAULT NULL,
  Status char(1) DEFAULT NULL,
  EntryFlag char(1) NOT NULL,
  ORGID bigint(4) NOT NULL,
  USER_ID bigint(7) NOT NULL,
  LANGID bigint(4) NOT NULL,
  LMODDATE datetime DEFAULT NULL,
  UPDATED_BY bigint(7) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (Itemid),
  KEY PK_itemcode (itemcode),
  KEY IX_itemname (name),
  KEY IX_itemgroup (itemgroup),
  KEY IX_itemsubgroup (itemsubgroup),
  KEY IX_orgid (ORGID)
);
--liquibase formatted sql
--changeset Kanchan:V20230110202956__CR_mm_purchaseorder_100120233.sql
CREATE TABLE mm_requisition_det (
  prdetid bigint(12) NOT NULL,
  prid bigint(12) NOT NULL,
  itemid bigint(12) NOT NULL,
  quantity double(12,2) NOT NULL,
  Status char(1) NOT NULL,
  podetref bigint(12) DEFAULT NULL,
  ORGID bigint(4) NOT NULL,
  USER_ID bigint(7) NOT NULL,
  LANGID bigint(4) NOT NULL,
  LMODDATE datetime DEFAULT NULL,
  UPDATED_BY bigint(7) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  UON_NAME varchar(100) DEFAULT NULL,
  PRIMARY KEY (prdetid),
  KEY FK_KEY_8 (itemid),
  KEY FK_KEY_9 (prid),
  CONSTRAINT FK_KEY_8 FOREIGN KEY (itemid) REFERENCES mm_itemmaster (Itemid),
  CONSTRAINT FK_KEY_9 FOREIGN KEY (prid) REFERENCES mm_requisition (prid)
);


