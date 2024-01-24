--liquibase formatted sql
--changeset nilima:V20180529145452__CR_TB_SW_VEREMEN_MAST_HIST_22052018.sql
CREATE TABLE TB_SW_VEREMEN_MAST_HIST (
  VEM_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VEM_ID bigint(12)  COMMENT 'Primary Key',
  VEM_METYPE bigint(12)  COMMENT 'Vechicle Maintenance Type',
  VEM_DATE date  COMMENT 'Date of Repair/Maintence',
  VE_VETYPE bigint(12)  COMMENT 'Vechicle Type',
  VE_ID bigint(12)  COMMENT 'Vechicle No',
  VEM_DOWNTIME bigint(3)  COMMENT 'Actual Downntime',
  VEM_DOWNTIMEUNIT bigint(12)  COMMENT 'Unit',
  VEM_READING bigint(12)  COMMENT 'Vechicle Reading During Repair/Maintenance',
  VEM_COSTINCURRED decimal(15,2)  COMMENT 'Total Cost Incurred',
  VEM_RECEIPTNO bigint(12)  COMMENT 'Vechicle Maintenance Receipt No',
  VEM_RECEIPTDATE datetime  COMMENT 'Vechicle Maintenance Receipt No',
  VEM_REASON varchar(100)  COMMENT 'Vechicle Maintenance Remark',
  H_STATUS char(1)  COMMENT 'Status I->Insert,U->update',    
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VEM_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Maintenance';