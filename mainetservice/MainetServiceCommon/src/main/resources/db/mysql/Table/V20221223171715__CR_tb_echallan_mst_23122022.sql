--liquibase formatted sql
--changeset Kanchan:V20221223171715__CR_tb_echallan_mst_23122022.sql
CREATE TABLE tb_echallan_mst (
  CHLN_ID bigint(12) NOT NULL,
  CHLN_NO varchar(10) DEFAULT NULL,
  CHALLAN_DATE date DEFAULT NULL,
  CHLN_TYPE varchar(50) NOT NULL,
  CHLN_DESC varchar(500) DEFAULT NULL,
  CHLN_AMT decimal(12,2) DEFAULT NULL,
  DUE_DATE date DEFAULT NULL,
  OFFN_NAME varchar(100) NOT NULL,
  OFFN_MOBNO varchar(30) NOT NULL,
  OFFN_EMAIL varchar(100) DEFAULT NULL,
  EVIDENCE_IMG blob,
  STATUS varchar(10) NOT NULL,
  From_Area varchar(50) NOT NULL,
  TO_Area varchar(50) NOT NULL,
  Officer_OnSite varchar(250) NOT NULL,
  ORGID int(10) NOT NULL,
  CREATED_BY int(10) DEFAULT NULL,
  CREATED_DATE datetime NOT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  UPDATED_BY int(10) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  RAID_NO varchar(50) DEFAULT NULL,
  REFERENCE_NO varchar(10) DEFAULT NULL,
  REMARK varchar(100) DEFAULT NULL,
  Locality varchar(100) DEFAULT NULL,
  PRIMARY KEY (CHLN_ID)
) ;
--liquibase formatted sql
--changeset Kanchan:V20221223171715__CR_tb_echallan_mst_231220221.sql
CREATE TABLE tb_echallan_itemdet (
  CHLN_ID bigint(12) NOT NULL,
  ITEM_ID bigint(12) NOT NULL,
  ITEM_NO varchar(10) DEFAULT NULL,
  ITEM_DESC varchar(50) NOT NULL,
  ITEM_QUANT int(10) NOT NULL,
  STORE_ID varchar(20) DEFAULT NULL,
  STATUS varchar(20) NOT NULL,
  ORGID int(10) NOT NULL,
  CREATED_BY int(10) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  UPDATED_BY int(10) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (ITEM_ID),
  KEY tb_echallan_itemdet_ibfk_1 (CHLN_ID),
  CONSTRAINT tb_echallan_itemdet_ibfk_1 FOREIGN KEY (CHLN_ID) REFERENCES tb_echallan_mst (CHLN_ID)
) ;
