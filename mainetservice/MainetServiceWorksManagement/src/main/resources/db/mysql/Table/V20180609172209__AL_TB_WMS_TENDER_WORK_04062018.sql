--liquibase formatted sql
--changeset nilima:V20180609172209__AL_TB_WMS_TENDER_WORK_04062018.sql
DROP TABLE IF EXISTS TB_WMS_TENDER_WORK;
--changeset nilima:V20180609172209__AL_TB_WMS_TENDER_WORK_040620181.sql
CREATE TABLE TB_WMS_TENDER_WORK (
  TNDW_ID bigint(12) NOT NULL,
  TND_ID bigint(12) NOT NULL,
  WORK_ID bigint(12) NOT NULL,
  WORK_ESTAMT varchar(45) DEFAULT NULL,
  TND_VENDER bigint(12) DEFAULT NULL COMMENT 'Vendor',
  TND_VED_CLASS bigint(12) DEFAULT NULL COMMENT 'Vendor Class',
  TND_SEC_AMOUNT decimal(15,2) DEFAULT NULL COMMENT 'Security Amount',
  TND_FEE_AMOUNT decimal(15,2) DEFAULT NULL COMMENT 'Tender Fee Amount',
  TND_VENWORKPE bigint(12) DEFAULT NULL,
  TND_VENWORKPE_UNIT bigint(12) DEFAULT NULL,
  TND_TYPE bigint(12) DEFAULT NULL COMMENT 'Amount->''A'',''%''->''P''',
  TND_VALUE decimal(15,2) DEFAULT NULL,
  TND_AMOUNT decimal(15,2) DEFAULT NULL,
  WORK_ASSIGNEE bigint(12) DEFAULT NULL,
  WORK_ASSIGNEDATE varchar(45) DEFAULT NULL,
  TND_STAMPFEE decimal(8,2) DEFAULT NULL,
  TND_NODAYAGREE bigint(12) DEFAULT NULL,
  TND_LOA_NO varchar(40) DEFAULT NULL,
  TND_LOA_DATE date DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has updated the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (TNDW_ID),
  KEY FK_TNDID_idx (TND_ID),
  KEY FK_TND_WORKID_idx (WORK_ID),
  CONSTRAINT FK_TNDID FOREIGN KEY (TND_ID) REFERENCES tb_wms_tender_mast (TND_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_TND_WORKID FOREIGN KEY (WORK_ID) REFERENCES tb_wms_workdefination (WORK_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
