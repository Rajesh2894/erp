--liquibase formatted sql
--changeset nilima:V20180616142851__CR_TB_SW_VEHICLEFUEL_INREC_DET_HIST_15062018.sql
CREATE TABLE TB_SW_VEHICLEFUEL_INREC_DET_HIST (
  INRECD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  INRECD_ID bigint(12)  COMMENT 'Primary Key',
  INREC_ID bigint(12)  COMMENT 'FK  TB_VEHICLEFUEL_INREC',
  VEF_ID bigint(12)  COMMENT 'FK  TB_SW_VEHICLEFUEL_MAST',
  INRECD_INVNO bigint(12)  COMMENT 'Invoice No.',
  INRECD_INVDATE date  COMMENT 'iNVoice Date',
  INRECD_INVAMT bigint(12)  COMMENT 'Invoice Amount',
  INRECD_EXPEN bigint(12) DEFAULT NULL COMMENT 'Invoice Expenditure Head',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (INRECD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Invoice Reconcilation Detail';