--liquibase formatted sql
--changeset nilima:V20180529145541__CR_TB_SW_VEHICLEFUEL_MAST_23052018.sql
CREATE TABLE TB_SW_VEHICLEFUEL_MAST (
  VEF_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  VE_VETYPE BIGINT(12) NULL COMMENT 'Vechicle Type',
  VE_ID BIGINT(12) NULL COMMENT 'Vechicle No',
  VEF_READING BIGINT(12) NULL COMMENT 'Vechicle Reading During Refueling',
  PU_ID BIGINT(12) NULL COMMENT 'Name of Pump',
  VEF_RECEIPTNO BIGINT(12) NULL COMMENT 'Receipt Number',
  VEF_RECEIPTDATE DATE NULL COMMENT 'Receipt Date',
  VEF_RMAMOUNT DECIMAL(15,2) NULL COMMENT 'Receipt Amount',
  VEF_DMNO BIGINT(12) NULL COMMENT 'Demand Number',
  VEF_DMDATE DATE NULL COMMENT 'Demand Date',
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VEF_ID))
COMMENT = 'Vehicle Fueling Master';
