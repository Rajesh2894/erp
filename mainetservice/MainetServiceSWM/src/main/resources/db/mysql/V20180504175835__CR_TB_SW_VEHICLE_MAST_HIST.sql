--liquibase formatted sql
--changeset nilima:V20180504175835__CR_TB_SW_VEHICLE_MAST_HIST.sql
CREATE TABLE TB_SW_VEHICLE_MAST_HIST (
  VE_ID_H bigint(12)  COMMENT 'primary key',
  VE_ID bigint(12)  COMMENT 'primary key',
  VE_GPS_DEVICEID varchar(15) DEFAULT NULL COMMENT 'Vehicle GPS Tracking Device ID',
  VE_VETYPE bigint(12)  COMMENT 'vehicle type (''VHT'')',
  VE_REG_NO varchar(15)  COMMENT 'vehicle registration number',
  VE_ENG_SRNO varchar(20)  COMMENT 'vehicle engine serial number',
  VE_CAPACITY decimal(10,2)  COMMENT 'vehicle capacity',
  VE_CAUNIT bigint(12)  COMMENT 'vehicle capacity unit',
  VE_STD_WEIGHT decimal(15,2)  COMMENT 'standard weight of vehicle',
  VE_WEUNIT bigint(12)  COMMENT 'vehicle weight unit',
  VE_CHASIS_SRNO varchar(20)  COMMENT 'vehicle chasis serial number',
  VE_MODEL varchar(200)  COMMENT 'make/model/manufacturer of the vehicle',
  VE_FLAG char(1)  COMMENT 'department owned vehicle y/n',
  VE_PUR_DATE date  COMMENT 'purchase date of the vehicle',
  VE_PUR_PRICE decimal(15,2)  COMMENT 'purchase price of the vehicle',
  VE_PUR_SOURCE varchar(200)  COMMENT 'source of purchase of the vehicle',
  VE_RENT_FROMDATE date  COMMENT 'Rented From Date',
  VE_RENT_TODATE date  COMMENT 'Rented To Date',
  VM_VENDORID bigint(12)  COMMENT 'Vender Name',
  VE_RENTAMT decimal(15,2)  COMMENT 'Vechicle Rent Amount',
  VE_REMARKS varchar(200)  COMMENT 'purpose of (purchase/Rent)',
  VE_ACTIVE char(1)  COMMENT 'Vechicle(Active->''Y'',InActive''N'')',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VE_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechical Master';
