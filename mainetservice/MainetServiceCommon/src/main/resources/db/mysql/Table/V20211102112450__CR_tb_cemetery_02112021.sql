--liquibase formatted sql
--changeset Kanchan:V20211102112450__CR_tb_cemetery_02112021.sql
CREATE TABLE tb_cemetery (
  CE_ID bigint(12) NOT NULL,
  orgid bigint(11) NOT NULL,
  CE_NAME varchar(200) COLLATE utf8mb4_bin NOT NULL,
  CE_ADDR varchar(400) COLLATE utf8mb4_bin NOT NULL,
  CPD_TYPE_ID bigint(12) DEFAULT NULL,
  CE_STATUS varchar(2) COLLATE utf8mb4_bin NOT NULL,
  USER_ID int(11) NOT NULL,
  LANG_ID int(11) NOT NULL,
  LMODDATE datetime NOT NULL,
  UPDATED_BY int(11) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  CE_NAME_MAR varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  CE_ADDR_MAR varchar(400) COLLATE utf8mb4_bin DEFAULT NULL,
  AGENCY_ID bigint(12) DEFAULT NULL,
  PRIMARY KEY (CE_ID,orgid),
  KEY FK_CE_CPD_TYPE_ID (CPD_TYPE_ID),
  CONSTRAINT FK_CE_CPD_TYPE_ID FOREIGN KEY (CPD_TYPE_ID) REFERENCES tb_comparam_det (CPD_ID)
) ;
