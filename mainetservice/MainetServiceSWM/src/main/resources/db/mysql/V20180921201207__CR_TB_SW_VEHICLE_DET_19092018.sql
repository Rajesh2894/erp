--liquibase formatted sql
--changeset nilima:V20180921201207__CR_TB_SW_VEHICLE_DET_19092018.sql
CREATE TABLE TB_SW_VEHICLE_DET (
  VED_ID bigint(12) NOT NULL COMMENT 'primary key',
  VE_ID bigint(12) NOT NULL COMMENT 'Foregin key FK_TB_SW_VEHICLE_MAST',
  COD_WAST1 bigint(12) NOT NULL COMMENT 'Waste Type',
  VE_CAPACITY decimal(15,2) NOT NULL COMMENT 'Waste Type Wise Capacity',
  VE_ACTIVE char(1) DEFAULT NULL COMMENT 'Waste Type (Active->''Y'',InActive''N'')',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VED_ID),
  KEY FK_VEID_idx (VE_ID),
  CONSTRAINT FK_VEID FOREIGN KEY (VE_ID) REFERENCES tb_sw_vehicle_mast (VE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechical Master Detail';