--liquibase formatted sql
--changeset nilima:V20180616142911__CR_TB_SW_VEHICLEFUEL_INREC_HIST_15062018.sql
CREATE TABLE TB_SW_VEHICLEFUEL_INREC_HIST (
  INREC_ID_H BIGINT(12) NOT NULL COMMENT 'Primary Key',
  INREC_ID BIGINT(12)  COMMENT 'Primary Key',
  PU_ID BIGINT(12)  COMMENT 'FK TB_SW_PUMP_MST',
  INREC_FROMDT DATE  COMMENT 'Invoice Reconsilation From Date',
  INREC_TODT DATE  COMMENT 'Invoice Reconsilation To Date',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID BIGINT(12)  COMMENT 'organization id',
  CREATED_BY BIGINT(12)  COMMENT 'user id who created the record',
  CREATED_DATE DATETIME  COMMENT 'record creation date',
  UPDATED_BY BIGINT(12)  COMMENT 'user id who updated the record',
  LG_IP_MAC VARCHAR(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (INREC_ID_H))
COMMENT = 'Reconcilation of fuel Invoice';