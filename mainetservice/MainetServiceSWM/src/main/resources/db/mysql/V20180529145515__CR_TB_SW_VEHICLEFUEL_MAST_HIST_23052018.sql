--liquibase formatted sql
--changeset nilima:V20180529145515__CR_TB_SW_VEHICLEFUEL_MAST_HIST_23052018.sql
CREATE TABLE TB_SW_VEHICLEFUEL_MAST_HIST(
  VEF_ID_H BIGINT(12) NOT NULL COMMENT 'Primary Key',
  VEF_ID BIGINT(12)  COMMENT 'Primary Key',
  VE_VETYPE BIGINT(12)  COMMENT 'Vechicle Type',
  VE_ID BIGINT(12)  COMMENT 'Vechicle No',
  VEF_READING BIGINT(12)  COMMENT 'Vechicle Reading During Refueling',
  PU_ID BIGINT(12)  COMMENT 'Name of Pump',
  VEF_RECEIPTNO BIGINT(12)  COMMENT 'Receipt Number',
  VEF_RECEIPTDATE DATE  COMMENT 'Receipt Date',
  VEF_RMAMOUNT DECIMAL(15,2)  COMMENT 'Receipt Amount',
  VEF_DMNO BIGINT(12)  COMMENT 'Demand Number',
  VEF_DMDATE DATE  COMMENT 'Demand Date',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',  
  ORGID BIGINT(12)  COMMENT 'organization id',
  CREATED_BY BIGINT(12)  COMMENT 'user id who created the record',
  CREATED_DATE DATETIME  COMMENT 'record creation date',
  UPDATED_BY BIGINT(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME  COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VEF_ID_H))
COMMENT = 'Vehicle Fueling Master history';