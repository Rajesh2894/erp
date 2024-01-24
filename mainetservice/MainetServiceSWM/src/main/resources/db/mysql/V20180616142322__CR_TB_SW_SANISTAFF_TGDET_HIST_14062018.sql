--liquibase formatted sql
--changeset nilima:V20180616142322__CR_TB_SW_SANISTAFF_TGDET_HIST_14062018.sql
CREATE TABLE TB_SW_SANISTAFF_TGDET_HIST (
  SAND_ID_H BIGINT(12) NOT NULL COMMENT 'Primary Key',
  SAND_ID BIGINT(12)  COMMENT 'Primary Key',
  SAN_ID BIGINT(12)  COMMENT 'TB_SW_SANISTAFF_TG',
  EMPID BIGINT(12)  COMMENT 'FK EMPLOYEE',
  RO_ID BIGINT(12)  COMMENT 'FK tb_sw_route_mast',
  COD_WAST1 BIGINT(12)  COMMENT 'Waste Type',
  COD_WAST2 BIGINT(12)  COMMENT 'Waste SubType',
  COD_WAST3 BIGINT(12) ,
  COD_WAST4 BIGINT(12) ,
  COD_WAST5 BIGINT(12)  COMMENT 'Waste SubType',
   DE_ID BIGINT(12)  COMMENT 'FK TB_SW_DESPOSAL_MAST',
  SAND_VOLUME BIGINT(20)  COMMENT 'Volume',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID BIGINT(12)  COMMENT 'organization id',
  CREATED_BY BIGINT(12)  COMMENT 'user id who created the record',
  CREATED_DATE DATETIME ,
  UPDATED_BY BIGINT(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME  COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SAND_ID_H))
COMMENT = 'SANITATION STAFF TARGET';