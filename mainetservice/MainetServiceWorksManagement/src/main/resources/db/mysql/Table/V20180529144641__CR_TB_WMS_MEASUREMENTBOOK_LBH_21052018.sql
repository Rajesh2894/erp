--liquibase formatted sql
--changeset nilima:V20180529144641__CR_TB_WMS_MEASUREMENTBOOK_LBH_21052018.sql
CREATE TABLE TB_WMS_MEASUREMENTBOOK_LBH (
  MB_LBHID bigint(12) NOT NULL COMMENT 'Primary Key',
  MBD_ID bigint(12) DEFAULT NULL,
  ME_ID bigint(12) DEFAULT NULL COMMENT 'FK  TB_WMS_MEASUREMENT_DETAIL',
  MB_PARTICULARE varchar(500) DEFAULT NULL COMMENT 'Measurement Particulare',
  MB_NOS_ACT bigint(5) DEFAULT NULL COMMENT 'Nos Actual',
  MB_VALUE_TYPE char(1) DEFAULT NULL COMMENT 'Value Type (Calculated->C,Direct->D,Formula->F)',
  MB_LENGTH decimal(7,2) DEFAULT NULL COMMENT 'Length',
  MB_BREADTH decimal(7,2) DEFAULT NULL COMMENT 'Breadth',
  MB_HEIGHT decimal(8,3) DEFAULT NULL COMMENT 'Height',
  MB_FORMULA varchar(100) DEFAULT NULL COMMENT 'Formula/Direct Value',
  MB_TOTAL decimal(12,2) DEFAULT NULL COMMENT 'Total Quantity',
  MB_TYPE char(1) DEFAULT NULL COMMENT 'Deviation Type (A->Addition,D->Deduction)',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (MB_LBHID),
  KEY FK_ME_ID_idx (ME_ID),
  CONSTRAINT FK_ME_ID FOREIGN KEY (ME_ID) REFERENCES tb_wms_measurement_detail (ME_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MeasurmentBook LBH';