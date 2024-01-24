--liquibase formatted sql
--changeset nilima:V20181227153152__CR_tb_wms_road_cutting_det_17122018.sql
CREATE TABLE TB_WMS_ROAD_CUTTING_DET (
  RCD_ID bigint(12) NOT NULL,
  RC_ID bigint(12) NOT NULL,
  RCD_TECTYPE bigint(12) DEFAULT NULL COMMENT 'Type of Technology',
  RCD_ROADDESC varchar(500) DEFAULT NULL COMMENT 'ROAD/ROUTE DESC',
  RCD_ROADtYPE bigint(12) DEFAULT NULL COMMENT 'Road Type ',
  RCD_NO bigint(12) DEFAULT NULL COMMENT 'Numbers',
  RCD_LENGTH decimal(15,3) DEFAULT NULL COMMENT 'Length in (Meter)',
  RCD_BREADTH decimal(15,3) DEFAULT NULL COMMENT 'Breadth (Meter)',
  RCD_HEIGHT decimal(15,3) DEFAULT NULL COMMENT 'Height ',
  RCD_DIAMETER decimal(15,3) DEFAULT NULL COMMENT 'Diameter ',
  LOC_ID bigint(12) DEFAULT NULL COMMENT 'Location ',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`RCD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
