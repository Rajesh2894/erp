--liquibase formatted sql
--changeset nilima:V20181120130605__CR_TB_WMS_MBCHEKLIST_MAST_HIST_16112018.sql
CREATE TABLE TB_WMS_MBCHEKLIST_MAST_HIST (
  MBC_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  MBC_ID bigint(12)  COMMENT 'Primary Key',
  MB_ID bigint(12)  COMMENT 'MB Id',
  MBC_INSPECTION_DT datetime  COMMENT 'Inspection Dt',
  MBC_DRAWINGNO varchar(50)  COMMENT 'Drawing No.',
  LOC_ID bigint(12)  COMMENT 'Location',
  MBC_CHKID varchar(50)  COMMENT 'Checklist prefix id',
  H_STATUS char(1)  COMMENT 'History Status',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MBC_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;