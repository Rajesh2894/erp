--liquibase formatted sql
--changeset nilima:V20180427113521__CR_TB_WMS_TENDER_WORK.sql
CREATE TABLE TB_WMS_TENDER_WORK (
  TNDW_ID bigint(12) NOT NULL,
  TND_ID bigint(12) NOT NULL,
  WORK_ID bigint(12) NOT NULL,
  WORK_ESTAMT varchar(45) DEFAULT NULL,
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