--liquibase formatted sql
--changeset nilima:V20180529145608__CR_TB_SW_VEHICLEFUEL_DET_HIST.sql
CREATE TABLE TB_SW_VEHICLEFUEL_DET_HIST (
  VEFD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VEFD_ID bigint(12)  COMMENT 'Primary Key',
  VEF_ID bigint(12)  COMMENT 'FK TB_SW_VEHICLEFUEL_MAST',
  PFU_ID bigint(12)  COMMENT 'FK tb_sw_pump_det',
  VEFD_QUANTITY bigint(3)  COMMENT 'Type of Item ',
  VEFD_UNIT bigint(12)  COMMENT 'Unit',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',  
  ORGID bigint(12) ,
  CREATED_BY bigint(12) ,
  CREATED_DATE datetime ,
  UPDATED_BY bigint(12) ,
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (VEFD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vehicle fueling detail History';