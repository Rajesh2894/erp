--liquibase formatted sql
--changeset nilima:V20180529182703__CR_TB_WMS_MEASUREMENTBOOK_LBH_HIS.sql
drop table if exists TB_WMS_MEASUREMENTBOOK_MAST_HIST;
--changeset nilima:V20180529182703__CR_TB_WMS_MEASUREMENTBOOK_LBH_HIS1.sql
CREATE TABLE TB_WMS_MEASUREMENTBOOK_LBH_HIS (
  MB_LBHID_H bigint(12)  COMMENT 'Primary Key',
  MB_LBHID bigint(12)  COMMENT 'Primary Key',
  ME_ID bigint(12)  COMMENT 'FK  TB_WMS_MEASUREMENT_DETAIL',
  MB_PARTICULARE varchar(500)  COMMENT 'Measurement Particulare',
  MB_NOS_ACT bigint(5)  COMMENT 'Nos Actual',
  MB_VALUE_TYPE char(1)  COMMENT 'Value Type (Calculated->C,Direct->D,Formula->F)',
  MB_LENGTH decimal(7,2)  COMMENT 'Length',
  MB_BREADTH decimal(7,2)  COMMENT 'Breadth',
  MB_HEIGHT decimal(7,2)  COMMENT 'Height',
  MB_FORMULA varchar(100)  COMMENT 'Formula/Direct Value',
  MB_TOTAL decimal(12,2)  COMMENT 'Total Quantity',
  MB_TYPE char(1)  COMMENT 'Deviation Type (A->Addition,D->Deduction)',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (MB_LBHID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MeasurmentBook LBH';
