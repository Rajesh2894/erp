--liquibase formatted sql
--changeset nilima:V20180504175517__CR_TB_SW_DISPOSAL_DET_HIST.sql
DROP TABLE IF EXISTS TB_SW_DISPOSAL_MAST_HIST;

--liquibase formatted sql
--changeset nilima:V20180504175517__CR_TB_SW_DISPOSAL_DET_HIST1.sql
CREATE TABLE TB_SW_DISPOSAL_MAST_HIST (
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
