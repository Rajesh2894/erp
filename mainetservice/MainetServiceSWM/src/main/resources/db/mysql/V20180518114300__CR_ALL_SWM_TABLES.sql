--liquibase formatted sql
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES.sql
drop table if exists tb_sw_disposal_det;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES4.sql
drop table if exists tb_sw_disposal_mast;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES5.sql
CREATE TABLE tb_sw_disposal_mast (
  DE_ID bigint(12) NOT NULL COMMENT 'Primay Key',
  DE_NAME varchar(100) NOT NULL COMMENT 'Disposal Site Name',
  DE_NAME_REG varchar(100) NOT NULL COMMENT 'Disposal Site Name REG',
  DE_AREA decimal(20,2) NOT NULL COMMENT 'Disposal Area',
  DE_AREA_UNIT bigint(12) DEFAULT NULL,
  DE_CATEGORY bigint(12) NOT NULL COMMENT 'Disposal Category',
  DE_ADDRESS varchar(200) NOT NULL COMMENT 'Address',
  DE_CAPACITY decimal(20,2) NOT NULL COMMENT 'Disposal Capacity',
  DE_CAPACITY_UNIT bigint(12) NOT NULL COMMENT 'Capacity Unit prefix',
  DE_GIS_ID varchar(15) DEFAULT NULL,
  DE_ACTIVE char(1) NOT NULL COMMENT 'Desposal Site (Active->''Y'',Inactive->''N'')',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (DE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Desposal Master';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES1.sql
CREATE TABLE tb_sw_disposal_det (
  DED_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  DE_ID bigint(12) NOT NULL COMMENT 'foregin key TB_SW_DEPOSAL_MAST',
  DE_WEST_TYPE bigint(12) NOT NULL COMMENT 'Desposal Waste Details',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (DED_ID),
  KEY FK_DE_ID_idx (DE_ID),
  CONSTRAINT FK_DE_ID FOREIGN KEY (DE_ID) REFERENCES tb_sw_disposal_mast (DE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Desposal Details';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES2.sql
drop table if exists tb_sw_disposal_det_hist;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES3.sql
CREATE TABLE tb_sw_disposal_det_hist (
  DED_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  DED_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  DE_ID bigint(12) DEFAULT NULL COMMENT 'foregin key TB_SW_DEPOSAL_MAST',
  DE_WEST_TYPE bigint(12) DEFAULT NULL COMMENT 'Desposal Waste Details',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (DED_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Desposal Details';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES6.sql
drop table if exists tb_sw_disposal_mast_hist;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES7.sql
CREATE TABLE tb_sw_disposal_mast_hist (
  DE_ID_H bigint(12) NOT NULL COMMENT 'Primay Key',
  DE_ID bigint(12) DEFAULT NULL COMMENT 'Primay Key',
  DE_NAME varchar(100) DEFAULT NULL COMMENT 'Disposal Site Name',
  DE_NAME_REG varchar(100) DEFAULT NULL COMMENT 'Disposal Site Name REG',
  DE_AREA decimal(20,2) DEFAULT NULL COMMENT 'Disposal Area',
  DE_AREA_UNIT bigint(12) DEFAULT NULL,
  DE_CATEGORY bigint(12) DEFAULT NULL COMMENT 'Disposal Category',
  DE_ADDRESS varchar(200) DEFAULT NULL COMMENT 'Address',
  DE_CAPACITY decimal(20,2) DEFAULT NULL COMMENT 'Disposal Capacity',
  DE_CAPACITY_UNIT bigint(12) DEFAULT NULL COMMENT 'Capacity Unit prefix',
  DE_GIS_ID varchar(15) DEFAULT NULL,
  DE_ACTIVE char(1) DEFAULT NULL COMMENT 'Desposal Site (Active->''Y'',Inactive->''N'')',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (DE_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Desposal Master';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES25.sql
drop table if exists tb_sw_pump_fuldet;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES12.sql
drop table if exists tb_sw_pump_mast;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES13.sql
CREATE TABLE tb_sw_pump_mast (
  PU_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  PU_PUTYPE bigint(12) NOT NULL COMMENT 'Type of Pump from prefix',
  PU_PUMPNAME varchar(100) NOT NULL COMMENT 'Pump Name',
  PU_ADDRESS varchar(200) NOT NULL COMMENT 'Pump Address',
  VM_VENDORID bigint(12) DEFAULT NULL COMMENT 'Vendor Id FK TB_VENDOR_MAST',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (PU_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Refilling Pump Station Detail';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES9.sql
CREATE TABLE tb_sw_pump_fuldet (
  pfu_id bigint(12) NOT NULL COMMENT 'Primary key',
  pu_id bigint(12) NOT NULL COMMENT 'FK TB_SW_PUMP_MST',
  pu_fuid bigint(12) NOT NULL COMMENT ' Fuel Type->Value from prefix',
  pu_fuunit bigint(12) NOT NULL COMMENT ' Fuel Unit->Value from prefix',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (pfu_id),
  KEY FK_PU_ID_idx (pu_id),
  CONSTRAINT FK_PU_ID FOREIGN KEY (pu_id) REFERENCES tb_sw_pump_mast (PU_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Refillin pump station detail';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES10.sql
drop table if exists tb_sw_pump_fuldet_hist;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES11.sql
CREATE TABLE tb_sw_pump_fuldet_hist (
  PFU_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  PFU_ID bigint(12) DEFAULT NULL COMMENT 'Primary key',
  PU_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_PUMP_MST',
  PU_FUID bigint(12) DEFAULT NULL COMMENT ' Fuel Type->Value from prefix',
  PU_FUUNIT bigint(12) DEFAULT NULL COMMENT ' Fuel Unit->Value from prefix',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (PFU_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Refillin pump station detail';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES14.sql
drop table if exists tb_sw_pump_mast_hist;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES15.sql
CREATE TABLE tb_sw_pump_mast_hist (
  PU_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  PU_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  PU_PUTYPE bigint(12) DEFAULT NULL COMMENT 'Type of Pump from prefix',
  PU_PUMPNAME varchar(100) DEFAULT NULL COMMENT 'Pump Name',
  PU_ADDRESS varchar(200) DEFAULT NULL COMMENT 'Pump Address',
  VM_VENDORID bigint(12) DEFAULT NULL COMMENT 'Vendor Id FK TB_VENDORMAST',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (PU_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Refilling Pump Station Detail';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES16.sql
drop table if exists tb_sw_roote_det;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES20.sql
drop table if exists tb_sw_roote_mast;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES21.sql
CREATE TABLE tb_sw_route_mast (
  RO_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  RO_NO varchar(50) DEFAULT NULL COMMENT 'Route Number',
  RO_NAME varchar(100) DEFAULT NULL COMMENT 'Route Name',
  RO_NAME_REG varchar(45) DEFAULT NULL COMMENT 'Route Name Regional',
  RO_START_POINT bigint(12) DEFAULT NULL COMMENT 'Starting Point',
  RO_END_POINT bigint(12) DEFAULT NULL COMMENT 'End Point',
  RO_DISTANCE decimal(12,2) DEFAULT NULL COMMENT 'Total Route Distance',
  RO_DISTANCE_UNIT bigint(12) DEFAULT NULL COMMENT 'Total Route Distance Unit',
  RO_VE_TYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (RO_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES17.sql
CREATE TABLE tb_sw_route_det (
  ROD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  RO_ID bigint(12) NOT NULL COMMENT 'FK tb_sw_root_mast',
  COD_WARD1 bigint(12) DEFAULT NULL COMMENT 'Ward',
  COD_WARD2 bigint(12) DEFAULT NULL COMMENT 'Zone',
  COD_WARD3 bigint(12) DEFAULT NULL,
  COD_WARD4 bigint(12) DEFAULT NULL,
  COD_WARD5 bigint(12) DEFAULT NULL,
  RO_COLL_POINTNAME varchar(500) NOT NULL COMMENT 'Collection Point Name',
  RO_COLL_POINTADD varchar(500) NOT NULL COMMENT 'Collection Point Address',
  RO_COLL_LATITUDE varchar(100) DEFAULT NULL COMMENT 'Latitude',
  RO_COLL_LONGITUDE varchar(100) DEFAULT NULL COMMENT 'Logitude',
  RO_SEQ_NO bigint(12) NOT NULL COMMENT 'Collection Point Sequence no.',
  RO_COLL_TYPE bigint(12) NOT NULL COMMENT 'Collection Type',
  RO_REFID varchar(50) DEFAULT NULL COMMENT 'Asset ID,GIS id,GPS id',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (ROD_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Root Detail';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES18.sql
drop table if exists tb_sw_roote_det_hist;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES19.sql
CREATE TABLE tb_sw_route_det_hist (
  ROD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  ROD_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  RO_ID bigint(12) DEFAULT NULL COMMENT 'FK tb_sw_root_mast',
  COD_WARD1 bigint(12) DEFAULT NULL COMMENT 'Ward',
  COD_WARD2 bigint(12) DEFAULT NULL COMMENT 'Zone',
  COD_WARD3 bigint(12) DEFAULT NULL COMMENT 'Block',
  COD_WARD4 bigint(12) DEFAULT NULL COMMENT 'route',
  COD_WARD5 bigint(12) DEFAULT NULL,
  RO_COLL_POINTNAME varchar(500) DEFAULT NULL COMMENT 'Collection Point Name',
  RO_COLL_POINTADD varchar(500) DEFAULT NULL COMMENT 'Collection Point Address',
  RO_COLL_LATITUDE varchar(100) DEFAULT NULL COMMENT 'Latitude',
  RO_COLL_LONGITUDE varchar(100) DEFAULT NULL COMMENT 'Logitude',
  RO_SEQ_NO bigint(12) DEFAULT NULL COMMENT 'Collection Point Sequence no.',
  RO_COLL_TYPE bigint(12) DEFAULT NULL COMMENT 'Collection Type',
  RO_REFID varchar(50) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (ROD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Root Detail';
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES22.sql
drop table if exists tb_sw_roote_mast_hist;
--changeset nilima:V20180518114300__CR_ALL_SWM_TABLES23.sql
CREATE TABLE tb_sw_route_mast_hist (
  RO_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  RO_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  RO_NO varchar(50) DEFAULT NULL COMMENT 'Route Number',
  RO_NAME varchar(100) DEFAULT NULL COMMENT 'Route Name',
  RO_NAME_REG varchar(45) DEFAULT NULL COMMENT 'Route Name Regional',
  RO_START_POINT bigint(12) DEFAULT NULL COMMENT 'Starting Point',
  RO_END_POINT bigint(12) DEFAULT NULL COMMENT 'End Point',
  RO_DISTANCE decimal(12,2) DEFAULT NULL COMMENT 'Total Route Distance',
  RO_DISTANCE_UNIT bigint(12) DEFAULT NULL COMMENT 'Total Route Distance Unit',
  RO_VE_TYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (RO_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




