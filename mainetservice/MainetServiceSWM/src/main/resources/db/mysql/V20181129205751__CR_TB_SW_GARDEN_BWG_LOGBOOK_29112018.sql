--liquibase formatted sql
--changeset nilima:V20181129205751__CR_TB_SW_GARDEN_BWG_LOGBOOK_29112018.sql
CREATE TABLE TB_SW_GARDEN_BWG_LOGBOOK (
  GALOG_ID   bigint(12) NOT NULL,
  GALOG_CPM  varchar(10) COMMENT 'Compost_prepration_Month',
  GALOG_CPY  varchar(10) COMMENT 'Compost_prepration_Year',
  GALOG_GBFC varchar(25) COMMENT 'Garden /Bulk /Food_Production_Center',
  COD_ID_OPER_LEVEL1 BIGINT(12) COMMENT 'WardNo',
  GALOG_PLANTC decimal(15,2) COMMENT 'Plant_capacity',
  GALOG_MFMSP char(1) COMMENT 'MFMS Plant ID',
  GALOG_SLRMCNAME varchar(500) COMMENT 'MFMS Plant ID',
  GALOG_SLRMCNO varchar(100) COMMENT 'SLRM_Centre_No',
  GALOG_SLRMDT Date COMMENT 'Date',
  VE_NO varchar(15) COMMENT 'Vehicle_No',
  GALOG_AWIPIT DECIMAL(15,2) ,
  GALOG_PITNO varchar(100) ,
  ORGID bigint(12) ,
  CREATED_BY bigint(12) ,
  CREATED_DATE datetime ,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (GALOG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
