--liquibase formatted sql
--changeset nilima:V20180613154344__AL_CR_SWMTABLE.sql
CREATE TABLE tb_sw_contvend_mapping (
  MAP_ID bigint(12) NOT NULL,
  CONT_ID bigint(12) NOT NULL COMMENT 'FK tb_contract_mast',
  MAP_TASK_ID varchar(50) DEFAULT NULL COMMENT 'Task Mapping',
  COD_WARD1 bigint(12) DEFAULT NULL,
  COD_WARD2 bigint(12) DEFAULT NULL,
  COD_WARD3 bigint(12) DEFAULT NULL,
  COD_WARD4 bigint(12) DEFAULT NULL,
  COD_WARD5 bigint(12) DEFAULT NULL,
  MAP_WASTETYPE bigint(12) DEFAULT NULL,
  MAP_GARBAGE bigint(12) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (MAP_ID),
  KEY FK_SWCONTID_idx (CONT_ID),
  CONSTRAINT FK_SWCONTID FOREIGN KEY (CONT_ID) REFERENCES tb_contract_mast (CONT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE1.sql
CREATE TABLE tb_sw_contvend_mapping_hist (
  MAP_ID_H bigint(12) NOT NULL,
  MAP_ID bigint(12) DEFAULT NULL,
  CONT_ID bigint(12) DEFAULT NULL COMMENT 'FK tb_contract_mast',
  MAP_TASK_ID varchar(50) DEFAULT NULL COMMENT 'Task Mapping',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (MAP_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE2.sql
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

--changeset nilima:V20180613154344__AL_CR_SWMTABLE3.sql
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

--changeset nilima:V20180613154344__AL_CR_SWMTABLE4.sql
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
  DED_ACTIVE char(1) DEFAULT NULL COMMENT 'Waste Active (Active->''Y'',Inactive->''N'')',
  PRIMARY KEY (DED_ID),
  KEY FK_DE_ID_idx (DE_ID),
  CONSTRAINT FK_DE_ID FOREIGN KEY (DE_ID) REFERENCES tb_sw_disposal_mast (DE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Desposal Details';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE5.sql
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
  DED_ACTIVE char(1) DEFAULT NULL COMMENT 'Waste Active (Active->''Y'',Inactive->''N'')',
  PRIMARY KEY (DED_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Desposal Details';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE6.sql
CREATE TABLE tb_sw_population_mast (
  POP_ID bigint(12) NOT NULL COMMENT 'Primary key',
  POP_YEAR bigint(12) NOT NULL COMMENT 'Population Year Pefix',
  COD_DWZID1 bigint(12) NOT NULL COMMENT 'Ward',
  COD_DWZID2 bigint(12) DEFAULT NULL COMMENT 'Zone',
  COD_DWZID3 bigint(12) DEFAULT NULL COMMENT 'Block',
  COD_DWZID4 bigint(12) DEFAULT NULL COMMENT 'Route',
  COD_DWZID5 bigint(12) DEFAULT NULL,
  POP_EST bigint(20) NOT NULL COMMENT 'Popultion',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (POP_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Population Mast';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE7.sql
CREATE TABLE tb_sw_population_mast_hist (
  POP_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  POP_ID bigint(12) DEFAULT NULL COMMENT 'Primary key',
  POP_YEAR bigint(12) DEFAULT NULL COMMENT 'Population Year Pefix',
  COD_DWZID1 bigint(12) DEFAULT NULL COMMENT 'Ward',
  COD_DWZID2 bigint(12) DEFAULT NULL COMMENT 'Zone',
  COD_DWZID3 bigint(12) DEFAULT NULL COMMENT 'Block',
  COD_DWZID4 bigint(12) DEFAULT NULL COMMENT 'Route',
  COD_DWZID5 bigint(12) DEFAULT NULL,
  POP_EST bigint(20) DEFAULT NULL COMMENT 'Popultion',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (POP_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Population Mast History';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE8.sql
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
---
--changeset nilima:V20180613154344__AL_CR_SWMTABLE43.sql
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

--changeset nilima:V20180613154344__AL_CR_SWMTABLE9.sql
CREATE TABLE tb_sw_pump_fuldet (
  pfu_id bigint(12) NOT NULL COMMENT 'Primary key',
  pu_id bigint(12) NOT NULL COMMENT 'FK TB_SW_PUMP_MST',
  pu_fuid bigint(12) NOT NULL COMMENT ' Fuel Type->Value from prefix',
  pu_fuunit bigint(12) NOT NULL COMMENT ' Fuel Unit->Value from prefix',
  PU_ACTIVE char(1) DEFAULT NULL COMMENT 'Applicable Y and N Not Applicable',
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

--changeset nilima:V20180613154344__AL_CR_SWMTABLE10.sql
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

--changeset nilima:V20180613154344__AL_CR_SWMTABLE11.sql
CREATE TABLE tb_sw_route_mast (
  RO_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  RO_NO varchar(50) NOT NULL COMMENT 'Route Number',
  RO_NAME varchar(100) NOT NULL COMMENT 'Route Name',
  RO_NAME_REG varchar(45) DEFAULT NULL COMMENT 'Route Name Regional',
  RO_START_POINT bigint(12) NOT NULL COMMENT 'Starting Point',
  RO_END_POINT bigint(12) NOT NULL COMMENT 'End Point',
  RO_DISTANCE decimal(12,2) NOT NULL COMMENT 'Total Route Distance',
  RO_DISTANCE_UNIT bigint(12) NOT NULL COMMENT 'Total Route Distance Unit',
  RO_VE_TYPE bigint(12) NOT NULL COMMENT 'Vechicle Type',
  DE_ID bigint(12) NOT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST',
  RO_DIST_DES decimal(12,2) NOT NULL COMMENT 'Distance from Disposal Site',
  RO_DIST_DES_UNIT decimal(12,2) NOT NULL COMMENT 'unit for Disposal Site',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL,
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (RO_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE12.sql
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
   DE_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST',
  RO_DIST_DES decimal(12,2) DEFAULT NULL COMMENT 'Distance from Disposal Site',
  RO_DIST_DES_UNIT decimal(12,2) DEFAULT NULL COMMENT 'unit for Disposal Site',
  PRIMARY KEY (RO_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE13.sql
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
  RO_ASSUM_QUANTITY bigint(12) DEFAULT NULL COMMENT 'Assuem Quantity',
  RO_COLL_ACTIVE char(1) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (ROD_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Root Detail';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE14.sql
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

--changeset nilima:V20180613154344__AL_CR_SWMTABLE15.sql
CREATE TABLE tb_sw_sanitation_mast (
  SAN_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  ASSET_ID bigint(12) DEFAULT NULL COMMENT 'ASSET_ID',
  SAN_TYPE bigint(12) NOT NULL COMMENT 'Public Toilet Type',
  SAN_NAME varchar(200) NOT NULL COMMENT 'Public Toilet Name',
  COD_WARD1 bigint(12) NOT NULL COMMENT 'zone',
  COD_WARD2 bigint(12) DEFAULT NULL COMMENT 'ward',
  COD_WARD3 bigint(12) DEFAULT NULL COMMENT 'road/street',
  COD_WARD4 bigint(12) DEFAULT NULL COMMENT 'colony/society/complex',
  COD_WARD5 bigint(12) DEFAULT NULL,
  SAN_SEAT_CNT varchar(45) DEFAULT NULL COMMENT 'seat count',
  SAN_GISNO varchar(50) DEFAULT NULL COMMENT 'gis no',
  LATTIUDE varchar(100) DEFAULT NULL COMMENT 'LATTIUDE',
  LONGITUDE varchar(100) DEFAULT NULL COMMENT 'LONGITUDE',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SAN_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE16.sql
CREATE TABLE tb_sw_sanitation_mast_hist (
  SAN_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  SAN_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  ASSET_ID bigint(12) DEFAULT NULL COMMENT 'ASSET_ID',
  SAN_TYPE bigint(12) DEFAULT NULL COMMENT 'Public Toilet Type',
  SAN_NAME varchar(200) DEFAULT NULL COMMENT 'Public Toilet Name',
  COD_WARD1 bigint(12) DEFAULT NULL COMMENT 'zone',
  COD_WARD2 bigint(12) DEFAULT NULL COMMENT 'ward',
  COD_WARD3 bigint(12) DEFAULT NULL COMMENT 'road/street',
  COD_WARD4 bigint(12) DEFAULT NULL COMMENT 'colony/society/complex',
  COD_WARD5 bigint(12) DEFAULT NULL,
  SAN_SEAT_CNT varchar(45) DEFAULT NULL COMMENT 'seat count',
  SAN_GISNO varchar(50) DEFAULT NULL COMMENT 'gis no',
  LATTIUDE varchar(100) DEFAULT NULL COMMENT 'LATTIUDE',
  LONGITUDE varchar(100) DEFAULT NULL COMMENT 'LONGITUDE',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SAN_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE17.sql
CREATE TABLE tb_sw_tripsheet (
  TRIP_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vehicle Id',
  DE_ID bigint(12) DEFAULT NULL COMMENT 'Disposal Site ',
  TRIP_DATE date NOT NULL COMMENT 'Trip Date',
  TRIP_DATA char(1) NOT NULL COMMENT 'U->Uploaded N->Normal',
  TRIP_DRIVERNAME varchar(100) DEFAULT NULL COMMENT 'Driver  Name',
  TRIP_INTIME datetime DEFAULT NULL COMMENT 'In Time',
  TRIP_OUTTIME datetime DEFAULT NULL COMMENT 'Out Time',
  TRIP_ENTWEIGHT bigint(12) DEFAULT NULL COMMENT 'Entry Weight',
  TRIP_EXITWEIGHT bigint(12) DEFAULT NULL,
  TRIP_TOTALGARBAGE bigint(20) DEFAULT NULL,
  TRIP_WESLIPNO varchar(50) DEFAULT NULL,
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TRIP_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tripsheet';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE18.sql
CREATE TABLE tb_sw_tripsheet_gdet (
  TRIPD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  TRIP_ID bigint(12) NOT NULL COMMENT 'FK TB_SW_TRIPSHEET',
  WAST_TYPE bigint(12) NOT NULL COMMENT 'Waste Type',
  TRIP_VOLUME bigint(20) NOT NULL COMMENT 'Volume',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TRIPD_ID),
  KEY FK_TRIPID_idx (TRIP_ID),
  CONSTRAINT FK_TRIPID FOREIGN KEY (TRIP_ID) REFERENCES tb_sw_tripsheet (TRIP_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE19.sql
CREATE TABLE tb_sw_tripsheet_gdet_hist (
  TRIPD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  TRIPD_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  TRIP_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_TRIPSHEET',
  WAST_TYPE bigint(12) DEFAULT NULL COMMENT 'Waste Type',
  TRIP_VOLUME bigint(20) DEFAULT NULL COMMENT 'Volume',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TRIPD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE20.sql
CREATE TABLE tb_sw_tripsheet_hist (
  TRIP_ID_H bigint(12) NOT NULL,
  TRIP_DATE date DEFAULT NULL,
  TRIP_DATA char(1) DEFAULT NULL COMMENT 'U->Uploaded N->Normal',
  TRIP_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vehicle Id',
  DE_ID bigint(12) DEFAULT NULL,
  TRIP_DRIVERNAME varchar(100) DEFAULT NULL COMMENT 'Driver  Name',
  TRIP_INTIME datetime DEFAULT NULL COMMENT 'In Time',
  TRIP_OUTTIME datetime DEFAULT NULL COMMENT 'Out Time',
  TRIP_ENTWEIGHT bigint(12) DEFAULT NULL COMMENT 'Entry Weight',
  TRIP_EXITWEIGHT bigint(12) DEFAULT NULL,
  TRIP_TOTALGARBAGE bigint(20) DEFAULT NULL,
  TRIP_WESLIPNO varchar(50) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (TRIP_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tripsheet Histroy';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE21.sql
CREATE TABLE tb_sw_vehicle_maintenance (
  VE_MEID bigint(12) NOT NULL COMMENT 'Primary Key',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  VE_MAINDAY bigint(3) DEFAULT NULL COMMENT 'Maintenance After',
  VE_MAINUNIT bigint(12) DEFAULT NULL COMMENT 'Maintenance Unit',
  VE_DOWNTIME bigint(3) DEFAULT NULL COMMENT 'Estimated Downtime',
  VE_DOWNTIMEUNIT bigint(12) DEFAULT NULL COMMENT 'Maintenance Unit',
  VE_MEACTIVE char(1) DEFAULT NULL COMMENT 'Active-''Y'',Inactive->''N''',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VE_MEID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Maintainacne Master';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE22.sql
CREATE TABLE tb_sw_vehicle_maintenance_hist (
  VE_MEID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VE_MEID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  VE_MAINDAY bigint(3) DEFAULT NULL COMMENT 'Maintenance After',
  VE_MAINUNIT bigint(12) DEFAULT NULL COMMENT 'Maintenance Unit',
  VE_DOWNTIME bigint(3) DEFAULT NULL COMMENT 'Estimated Downtime',
  VE_DOWNTIMEUNIT bigint(12) DEFAULT NULL COMMENT 'Maintenance Unit',
  VE_MEACTIVE char(1) DEFAULT NULL COMMENT 'Active-''Y'',Inactive->''N''',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VE_MEID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Maintainacne Master';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE23.sql
CREATE TABLE tb_sw_vehicle_mast (
  VE_ID bigint(12) NOT NULL COMMENT 'primary key',
  VE_GPS_DEVICEID varchar(15) DEFAULT NULL COMMENT 'Vehicle GPS Tracking Device ID',
  VE_VETYPE bigint(12) NOT NULL COMMENT 'vehicle type (''VHT'')',
  VE_REG_NO varchar(15) NOT NULL COMMENT 'vehicle registration number',
  VE_ENG_SRNO varchar(20) NOT NULL COMMENT 'vehicle engine serial number',
  VE_CAPACITY decimal(10,2) NOT NULL COMMENT 'vehicle capacity',
  VE_CAUNIT bigint(12) NOT NULL COMMENT 'vehicle capacity unit',
  VE_STD_WEIGHT decimal(15,2) NOT NULL COMMENT 'standard weight of vehicle',
  VE_WEUNIT bigint(12) NOT NULL COMMENT 'vehicle weight unit',
  VE_CHASIS_SRNO varchar(20) NOT NULL COMMENT 'vehicle chasis serial number',
  VE_MODEL varchar(200) NOT NULL COMMENT 'make/model/manufacturer of the vehicle',
  VE_FLAG char(1) NOT NULL COMMENT 'department owned vehicle y/n',
  VE_PUR_DATE date DEFAULT NULL COMMENT 'purchase date of the vehicle',
  VE_PUR_PRICE decimal(15,2) DEFAULT NULL COMMENT 'purchase price of the vehicle',
  VE_PUR_SOURCE varchar(200) DEFAULT NULL COMMENT 'source of purchase of the vehicle',
  VE_RENT_FROMDATE date DEFAULT NULL COMMENT 'Rented From Date',
  VE_RENT_TODATE date DEFAULT NULL COMMENT 'Rented To Date',
  VM_VENDORID bigint(12) DEFAULT NULL COMMENT 'Vender Name',
  VE_RENTAMT decimal(15,2) DEFAULT NULL COMMENT 'Vechicle Rent Amount',
  VE_REMARKS varchar(200) DEFAULT NULL COMMENT 'purpose of (purchase/Rent)',
  ASSET_ID bigint(12) DEFAULT NULL COMMENT 'Asset Id',
  ASSET_NO varchar(200) DEFAULT NULL COMMENT 'Asset No',
  VE_ACTIVE char(1) DEFAULT NULL COMMENT 'Vechicle(Active->''Y'',InActive''N'')',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechical Master';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE24.sql
CREATE TABLE tb_sw_vehicle_mast_hist (
  VE_ID_H bigint(12) NOT NULL COMMENT 'primary key',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'primary key',
  VE_GPS_DEVICEID varchar(15) DEFAULT NULL COMMENT 'Vehicle GPS Tracking Device ID',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'vehicle type (''VHT'')',
  VE_REG_NO varchar(15) DEFAULT NULL COMMENT 'vehicle registration number',
  VE_ENG_SRNO varchar(20) DEFAULT NULL COMMENT 'vehicle engine serial number',
  VE_CAPACITY decimal(10,2) DEFAULT NULL COMMENT 'vehicle capacity',
  VE_CAUNIT bigint(12) DEFAULT NULL COMMENT 'vehicle capacity unit',
  VE_STD_WEIGHT decimal(15,2) DEFAULT NULL COMMENT 'standard weight of vehicle',
  VE_WEUNIT bigint(12) DEFAULT NULL COMMENT 'vehicle weight unit',
  VE_CHASIS_SRNO varchar(20) DEFAULT NULL COMMENT 'vehicle chasis serial number',
  VE_MODEL varchar(200) DEFAULT NULL COMMENT 'make/model/manufacturer of the vehicle',
  VE_FLAG char(1) DEFAULT NULL COMMENT 'department owned vehicle y/n',
  VE_PUR_DATE date DEFAULT NULL COMMENT 'purchase date of the vehicle',
  VE_PUR_PRICE decimal(15,2) DEFAULT NULL COMMENT 'purchase price of the vehicle',
  VE_PUR_SOURCE varchar(200) DEFAULT NULL COMMENT 'source of purchase of the vehicle',
  VE_RENT_FROMDATE date DEFAULT NULL COMMENT 'Rented From Date',
  VE_RENT_TODATE date DEFAULT NULL COMMENT 'Rented To Date',
  VM_VENDORID bigint(12) DEFAULT NULL COMMENT 'Vender Name',
  VE_RENTAMT decimal(15,2) DEFAULT NULL COMMENT 'Vechicle Rent Amount',
  VE_REMARKS varchar(200) DEFAULT NULL COMMENT 'purpose of (purchase/Rent)',
  VE_ACTIVE char(1) DEFAULT NULL COMMENT 'Vechicle(Active->''Y'',InActive''N'')',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  ASSET_ID bigint(12) DEFAULT NULL COMMENT 'Asset ID',
  ASSET_NO varchar(200) DEFAULT NULL COMMENT 'Asset No',
  PRIMARY KEY (VE_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechical Master';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE25.sql
CREATE TABLE tb_sw_vehicle_scheduling (
  VES_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'vehicle type (''VHT'')',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vender Id',
  VES_FROMDT date DEFAULT NULL COMMENT 'Schedule From Date',
  VES_TODT date DEFAULT NULL COMMENT 'Schedule To Date',
  VES_REOCC char(1) DEFAULT NULL COMMENT 'Reoccurance(D->Daily,W->Weekly,M->)',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VES_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Scheduling Mast';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE26.sql
CREATE TABLE tb_sw_vehicle_scheduling_hist (
  VES_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VES_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'vehicle type (''VHT'')',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vender Id',
  VES_FROMDT date DEFAULT NULL COMMENT 'Schedule From Date',
  VES_TODT date DEFAULT NULL COMMENT 'Schedule To Date',
  VES_REOCC char(1) DEFAULT NULL COMMENT 'Reoccurance(D->Daily,W->Weekly,M->)',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VES_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Scheduling Mast Hist';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE27.sql
CREATE TABLE tb_sw_vehicle_scheddet (
  VESD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  VES_ID bigint(12) NOT NULL COMMENT 'FK TB_SW_VEHICLE_SCHEDULING',
  RO_ID bigint(12) NOT NULL COMMENT 'FK tb_sw_route_mast',
  VES_STARTIME datetime NOT NULL,
  VES_ENDTIME datetime NOT NULL,
  VES_WEEKDAY int(5) DEFAULT NULL COMMENT 'Week Day',
  VES_MONTH int(5) DEFAULT NULL COMMENT 'Week Month',
  VES_COLL_TYPE bigint(12) DEFAULT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VESD_ID),
  KEY FK_VES_ID_idx (VES_ID),
  KEY FK_RO_ID_idx (RO_ID),
  CONSTRAINT FK_RO_ID FOREIGN KEY (RO_ID) REFERENCES tb_sw_route_mast (RO_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_VES_ID FOREIGN KEY (VES_ID) REFERENCES tb_sw_vehicle_scheduling (VES_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Schedule Detail';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE28.sql
CREATE TABLE tb_sw_vehicle_scheddet_hist (
  VESD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VESD_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VES_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_VEHICLE_SCHEDULING',
  RO_ID bigint(12) DEFAULT NULL COMMENT 'FK tb_sw_route_mast',
  VES_STARTIME datetime DEFAULT NULL,
  VES_ENDTIME datetime DEFAULT NULL,
  VES_WEEKDAY int(5) DEFAULT NULL COMMENT 'Week Day',
  VES_MONTH int(5) DEFAULT NULL COMMENT 'Week Month',
  VES_COLL_TYPE bigint(12) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VESD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Schedule Detail History';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE29.sql
CREATE TABLE tb_sw_vehiclefuel_mast (
  VEF_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  VEF_DATE date NOT NULL COMMENT 'Vehicle Fueling date',
  VE_VETYPE bigint(12) NOT NULL COMMENT 'Vechicle Type',
  VE_ID bigint(12) NOT NULL COMMENT 'Vechicle No',
  VEF_READING bigint(12) NOT NULL COMMENT 'Vechicle Reading During Refueling',
  VEF_DRIVERNAME varchar(50) NOT NULL COMMENT 'Vechicle Fueling Driver Name',
  PU_ID bigint(12) NOT NULL COMMENT 'Name of Pump',
  VEF_DMNO bigint(12) NOT NULL COMMENT 'Demand Number',
  VEF_DMDATE date NOT NULL COMMENT 'Demand Date',
  VEF_RECEIPTNO bigint(12) DEFAULT NULL COMMENT 'Receipt Number',
  VEF_RECEIPTDATE date DEFAULT NULL COMMENT 'Receipt Date',
  VEF_RMAMOUNT decimal(15,2) DEFAULT NULL COMMENT 'Receipt Amount',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VEF_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vehicle Fueling Master';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE30.sql
CREATE TABLE tb_sw_vehiclefuel_mast_hist (
  VEF_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VEF_DATE date DEFAULT NULL,
  VEF_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vechicle No',
  VEF_READING bigint(12) DEFAULT NULL COMMENT 'Vechicle Reading During Refueling',
  VEF_DRIVERNAME varchar(50) DEFAULT NULL,
  PU_ID bigint(12) DEFAULT NULL COMMENT 'Name of Pump',
  VEF_RECEIPTNO bigint(12) DEFAULT NULL COMMENT 'Receipt Number',
  VEF_RECEIPTDATE date DEFAULT NULL COMMENT 'Receipt Date',
  VEF_RMAMOUNT decimal(15,2) DEFAULT NULL COMMENT 'Receipt Amount',
  VEF_DMNO bigint(12) DEFAULT NULL COMMENT 'Demand Number',
  VEF_DMDATE date DEFAULT NULL COMMENT 'Demand Date',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VEF_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vehicle Fueling Master history';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE31.sql
CREATE TABLE tb_sw_vehiclefuel_det (
  VEFD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  VEF_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_VEHICLEFUEL_MAST',
  PFU_ID bigint(12) DEFAULT NULL COMMENT 'FK tb_sw_pump_det',
  VEFD_QUANTITY bigint(3) DEFAULT NULL COMMENT 'Type of Item ',
  VEFD_UNIT bigint(12) DEFAULT NULL COMMENT 'Unit',
  VEFD_COST decimal(15,2) DEFAULT NULL COMMENT 'Cost',
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (VEFD_ID),
  KEY FK_VEF_ID_idx (VEF_ID),
  KEY FK_PFU_ID_idx (PFU_ID),
  CONSTRAINT FK_PFU_ID FOREIGN KEY (PFU_ID) REFERENCES tb_sw_pump_fuldet (pfu_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_VEF_ID FOREIGN KEY (VEF_ID) REFERENCES tb_sw_vehiclefuel_mast (VEF_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vehicle fueling detail ';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE32.sql
CREATE TABLE tb_sw_vehiclefuel_det_hist (
  VEFD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VEFD_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VEF_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_VEHICLEFUEL_MAST',
  PFU_ID bigint(12) DEFAULT NULL COMMENT 'FK tb_sw_pump_det',
  VEFD_QUANTITY bigint(3) DEFAULT NULL COMMENT 'Type of Item ',
  VEFD_UNIT bigint(12) DEFAULT NULL COMMENT 'Unit',
  VEFD_COST decimal(15,2) DEFAULT NULL COMMENT 'Cost',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL,
  CREATED_BY bigint(12) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (VEFD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vehicle fueling detail History';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE33.sql
CREATE TABLE tb_sw_veremen_mast (
  VEM_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  VEM_METYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Maintenance Type',
  VEM_DATE date DEFAULT NULL COMMENT 'Date of Repair/Maintence',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vechicle No',
  VEM_DOWNTIME bigint(3) DEFAULT NULL COMMENT 'Actual Downntime',
  VEM_DOWNTIMEUNIT bigint(12) DEFAULT NULL COMMENT 'Unit',
  VEM_READING bigint(12) DEFAULT NULL COMMENT 'Vechicle Reading During Repair/Maintenance',
  VEM_COSTINCURRED decimal(15,2) DEFAULT NULL COMMENT 'Total Cost Incurred',
  VEM_RECEIPTNO bigint(12) DEFAULT NULL COMMENT 'Vechicle Maintenance Receipt No',
  VEM_RECEIPTDATE datetime DEFAULT NULL COMMENT 'Vechicle Maintenance Receipt No',
  VEM_REASON varchar(100) DEFAULT NULL COMMENT 'Vechicle Maintenance Remark',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VEM_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Maintenance';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE34.sql
CREATE TABLE tb_sw_veremen_mast_hist (
  VEM_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  VEM_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VEM_METYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Maintenance Type',
  VEM_DATE date DEFAULT NULL COMMENT 'Date of Repair/Maintence',
  VE_VETYPE bigint(12) DEFAULT NULL COMMENT 'Vechicle Type',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vechicle No',
  VEM_DOWNTIME bigint(3) DEFAULT NULL COMMENT 'Actual Downntime',
  VEM_DOWNTIMEUNIT bigint(12) DEFAULT NULL COMMENT 'Unit',
  VEM_READING bigint(12) DEFAULT NULL COMMENT 'Vechicle Reading During Repair/Maintenance',
  VEM_COSTINCURRED decimal(15,2) DEFAULT NULL COMMENT 'Total Cost Incurred',
  VEM_RECEIPTNO bigint(12) DEFAULT NULL COMMENT 'Vechicle Maintenance Receipt No',
  VEM_RECEIPTDATE datetime DEFAULT NULL COMMENT 'Vechicle Maintenance Receipt No',
  VEM_REASON varchar(100) DEFAULT NULL COMMENT 'Vechicle Maintenance Remark',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,U->update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VEM_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Vechicle Maintenance';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE35.sql
CREATE TABLE tb_sw_wasteseg (
  GR_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  DE_ID bigint(12) NOT NULL COMMENT 'Disposal Site',
  GR_DATE date NOT NULL COMMENT 'Garbage Sagrigation date',
  GR_TOTAL bigint(12) DEFAULT NULL COMMENT 'Total Garbage Collected',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (GR_ID),
  KEY FK_WEDEID_idx (DE_ID),
  CONSTRAINT FK_WEDEID FOREIGN KEY (DE_ID) REFERENCES tb_sw_disposal_mast (DE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='waste Segregation';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE36.sql
CREATE TABLE tb_sw_wasteseg_det (
  GRD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  GR_ID bigint(12) NOT NULL COMMENT 'FK TB_SW_WASTESEG',
  COD_WAST1 bigint(12) NOT NULL COMMENT 'Waste Type\n',
  COD_WAST2 bigint(12) DEFAULT NULL COMMENT 'Waste SubType',
  COD_WAST3 bigint(12) DEFAULT NULL COMMENT 'Waste SubType',
  COD_WAST4 bigint(12) DEFAULT NULL COMMENT 'Waste SubType',
  COD_WAST5 bigint(12) DEFAULT NULL COMMENT 'Waste SubType',
  TRIP_VOLUME bigint(20) NOT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (GRD_ID),
  KEY FK_GRID_idx (GR_ID),
  CONSTRAINT FK_GRID FOREIGN KEY (GR_ID) REFERENCES tb_sw_wasteseg (GR_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Waste Garbage Detail';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE37.sql
CREATE TABLE tb_sw_wasteseg_det_hist (
  GRD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  GRD_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  GR_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_WASTESEG',
  COD_WAST1 bigint(12) DEFAULT NULL COMMENT 'Waste Type\n',
  COD_WAST2 bigint(12) DEFAULT NULL COMMENT 'Waste SubType',
  COD_WAST3 bigint(12) DEFAULT NULL COMMENT 'Waste SubType',
  COD_WAST4 bigint(12) DEFAULT NULL COMMENT 'Waste SubType',
  COD_WAST5 bigint(12) DEFAULT NULL COMMENT 'Waste SubType',
  TRIP_VOLUME bigint(20) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (GRD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Waste Segregation Detail History';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE38.sql
CREATE TABLE tb_sw_wasteseg_hist (
  GR_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  GR_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  DE_ID bigint(12) DEFAULT NULL COMMENT 'Disposal Site',
  GR_DATE date DEFAULT NULL COMMENT 'Garbage Sagrigation date',
  GR_TOTAL bigint(12) DEFAULT NULL COMMENT 'Total Garbage Collected',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (GR_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='waste Segregation history';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE39.sql
CREATE TABLE tb_sw_employee_scheduling (
  EMS_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  EMS_TYPE bigint(12) NOT NULL COMMENT 'Schedule Type',
  EMS_FROMDATE datetime NOT NULL COMMENT 'Schedule From date',
  EMS_TODATE datetime NOT NULL COMMENT 'Schedule To date',
  EMS_REOCC char(1) NOT NULL COMMENT 'Reoccurance(D->Daily,W->Weekly,M->)',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (EMS_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Employee Scheduling';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE40.sql
CREATE TABLE tb_sw_employee_scheduling_hist (
  EMS_ID_H bigint(12) NOT NULL,
  EMS_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  EMS_TYPE bigint(12) DEFAULT NULL COMMENT 'Schedule Type',
  EMS_FROMDATE datetime DEFAULT NULL COMMENT 'Schedule From date',
  EMS_TODATE datetime DEFAULT NULL COMMENT 'Schedule To date',
  EMS_REOCC char(1) DEFAULT NULL COMMENT 'Reoccurance(D->Daily,W->Weekly,M->)',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (EMS_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Employee Scheduling history';

--changeset nilima:V20180613154344__AL_CR_SWMTABLE41.sql
CREATE TABLE tb_sw_employee_scheddet (
  EMSD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  EMS_ID bigint(12) NOT NULL COMMENT 'FK TB_SW_EMPLOYEE_SCHEDULING',
  EMPID bigint(12) NOT NULL COMMENT 'FK EMPLOYEE',
  DE_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST',
  EMSD_COLL_TYPE bigint(12) DEFAULT NULL COMMENT 'Collection Type',
  LOC_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_LOCATION_MAST',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_VEHICLE_MAST',
  RO_ID bigint(12) DEFAULT NULL,
  COD_WARD1 bigint(12) DEFAULT NULL COMMENT 'Ward',
  COD_WARD2 bigint(12) DEFAULT NULL COMMENT 'Zone',
  COD_WARD3 bigint(12) DEFAULT NULL COMMENT 'Block',
  COD_WARD4 bigint(12) DEFAULT NULL,
  COD_WARD5 bigint(12) DEFAULT NULL,
  EMSD_STARTTIME datetime DEFAULT NULL COMMENT 'From Time',
  EMSD_ENDTIME datetime DEFAULT NULL COMMENT 'To Time',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL COMMENT 'datetime ',
  PRIMARY KEY (EMSD_ID),
  KEY FK_EMPSCH_idx (EMS_ID),
  KEY FK_EMPID_idx (EMPID),
  KEY FK_DEID_idx (DE_ID),
  KEY FK_LOCID_idx (LOC_ID),
  KEY FK_VEID_idx (VE_ID),
  KEY FK_ROID_idx (RO_ID),
  CONSTRAINT FK_EMPSCH FOREIGN KEY (EMS_ID) REFERENCES tb_sw_employee_scheduling (EMS_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_SCHDEID FOREIGN KEY (DE_ID) REFERENCES tb_sw_disposal_mast (DE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_SCHEMPID FOREIGN KEY (EMPID) REFERENCES employee (EMPID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_SCHLOCID FOREIGN KEY (LOC_ID) REFERENCES tb_location_mas (LOC_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_SCHROID FOREIGN KEY (RO_ID) REFERENCES tb_sw_route_mast (RO_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_SCHVEID FOREIGN KEY (VE_ID) REFERENCES tb_sw_vehicle_mast (VE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset nilima:V20180613154344__AL_CR_SWMTABLE42.sql
CREATE TABLE tb_sw_employee_scheddet_hist (
  EMSD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  EMSD_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  EMS_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_EMPLOYEE_SCHEDULING',
  EMPID bigint(12) DEFAULT NULL COMMENT 'FK EMPLOYEE',
  DE_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_DESPOSAL_MAST',
  EMSD_COLL_TYPE bigint(12) DEFAULT NULL COMMENT 'Collection Type',
  LOC_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_LOCATION_MAST',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_SW_VEHICLE_MAST',
  RO_ID bigint(12) DEFAULT NULL,
  COD_WARD1 bigint(12) DEFAULT NULL COMMENT 'Ward',
  COD_WARD2 bigint(12) DEFAULT NULL COMMENT 'Zone',
  COD_WARD3 bigint(12) DEFAULT NULL COMMENT 'Block',
  COD_WARD4 bigint(12) DEFAULT NULL,
  COD_WARD5 bigint(12) DEFAULT NULL,
  EMSD_STARTTIME datetime DEFAULT NULL COMMENT 'From Time',
  EMSD_ENDTIME datetime DEFAULT NULL COMMENT 'To Time',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  CREATED_DATE datetime DEFAULT NULL COMMENT 'datetime ',
  PRIMARY KEY (EMSD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

