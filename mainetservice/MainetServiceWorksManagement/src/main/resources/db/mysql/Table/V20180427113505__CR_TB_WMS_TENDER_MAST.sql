--liquibase formatted sql
--changeset nilima:V20180427113505__CR_TB_WMS_TENDER_MAST.sql
drop table if exists TB_WMS_TENDER_MAST;
--liquibase formatted sql
--changeset nilima:V20180427113505__CR_TB_WMS_TENDER_MAST1.sql
CREATE TABLE TB_WMS_TENDER_MAST (
  TND_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  PROJ_ID bigint(12) NOT NULL COMMENT 'Project Id (FK_TB_WMS_PROJ_MAS)',
  TND_CATEGORY bigint(12) NOT NULL COMMENT 'Tender Category',
  TND_RSO_NO varchar(40) DEFAULT NULL,
  TND_RSO_DATE datetime DEFAULT NULL,
  TND_PUBLISH_DATE date DEFAULT NULL COMMENT 'Tender Publish Date',
  TND_ISSUE_FROMDATE datetime DEFAULT NULL,
  TND_ISSUE_TODATE datetime DEFAULT NULL COMMENT 'Tender Technical Open Date',
  TND_TECOPEN_DATE datetime DEFAULT NULL,
  TND_FINOPEN_DATE datetime DEFAULT NULL COMMENT 'Tender Financiale Open Date',
  TND_INITIATIONNO varchar(50) DEFAULT NULL COMMENT 'Tender Initiation No.',
  TND_INITIATIONDATE datetime DEFAULT NULL COMMENT 'Tender Initiation Date',
  TND_ESTAMT decimal(15,2) DEFAULT NULL,
  TND_NO varchar(40) DEFAULT NULL COMMENT 'Tender Number',
  TND_DATE datetime DEFAULT NULL COMMENT 'Tender Date',
  TND_EMD_AMOUNT decimal(15,2) DEFAULT NULL COMMENT 'Tender Earnest Money Deposite Amount',
  TND_SEC_AMOUNT decimal(15,2) DEFAULT NULL COMMENT 'Tender Security Amount',
  TND_AMOUNT decimal(15,2) DEFAULT NULL COMMENT 'Tender Amount',
  TND_VENDER bigint(12) DEFAULT NULL COMMENT 'Tender Vender',
  TND_STATUS char(1) NOT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TND_ID),
  KEY FK_PROJ_ID_idx (PROJ_ID),
  CONSTRAINT FK_PROJ_ID FOREIGN KEY (PROJ_ID) REFERENCES tb_wms_project_mast (PROJ_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
