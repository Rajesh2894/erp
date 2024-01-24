--liquibase formatted sql
--changeset nilima:V20181120130545__CR_TB_WMS_MBCHEKLIST_MAST_16112018.sql
CREATE TABLE TB_WMS_MBCHEKLIST_MAST (
  MBC_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  MB_ID bigint(12) NOT NULL COMMENT 'MB Id',
  MBC_INSPECTION_DT datetime  COMMENT 'Inspection Dt',
  MBC_DRAWINGNO varchar(50)  COMMENT 'Drawing No.',
  LOC_ID bigint(12)  COMMENT 'Location',
  MBC_CHKID varchar(50)  COMMENT 'Checklist prefix id',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MBC_ID),
  KEY FK_MBC_MBID_idx (MB_ID),
  CONSTRAINT FK_MBC_MBID FOREIGN KEY (MB_ID) REFERENCES tb_wms_measurementbook_mast (MB_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	

