--liquibase formatted sql
--changeset nilima:V20180518114822__CR_TB_WMS_MEASUREMENTBOOK_DET.sql
drop table if exists TB_WMS_MEASUREMENTBOOK_DET;
--changeset nilima:V20180518114822__CR_TB_WMS_MEASUREMENTBOOK_DET1.sql
CREATE TABLE TB_WMS_MEASUREMENTBOOK_DET (
  MBD_ID bigint(12) NOT NULL COMMENT 'primary key',
  MB_ID bigint(12) NOT NULL COMMENT 'Foregin Key TB_WMS_MEASUREMENTBOOK_MAST',
  WORKE_ESTIMATE_TYPE char(1) NOT NULL COMMENT '(Estimate from SOR->''S'',From Previous Estimate->''P'',Upload Estimate->''U'')',
  WORKE_ID bigint(12) NOT NULL COMMENT 'Foregin Key TB_WMS_WORKESTIMATE_MAS',
  WORKE_ACTUAL_QTY bigint(5) DEFAULT NULL COMMENT 'Actual Quantity',
  WORKE_ACTUAL_AMOUNT decimal(20,2) DEFAULT NULL COMMENT 'Actual Amount',
  WORKE_FLAG char(3) DEFAULT NULL COMMENT '(S->''SOR ITEM'',''M''->Material,''RO''->Royalty,''LO''->LOAD,''UN''->''Unload'',''LE''->''Lead'',''LF''->LIFT,''ST''->''Stacking'',''L''->Labour,''C''->Machinary,''N''->''NON-SOR'')',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (MBD_ID),
  KEY FK_MBID_idx (MB_ID),
  KEY FK_WORKEID_idx (WORKE_ID),
  CONSTRAINT FK_MBID FOREIGN KEY (MB_ID) REFERENCES tb_wms_measurementbook_mast (MB_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_WORKEID FOREIGN KEY (WORKE_ID) REFERENCES tb_wms_workestimate_mas (WORKE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Measurement Book Detail';