--liquibase formatted sql
--changeset nilima:V20171214134526__CR_tb_wms_sor_det.sql
CREATE TABLE tb_wms_sor_det (
  SORD_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  SOR_ID bigint(12) NOT NULL COMMENT 'Schedule Rate Name',
  SORD_ITEMNO varchar(50) NOT NULL COMMENT 'Item Number',
  SORD_DESCRIPTION varchar(2000) NOT NULL COMMENT 'Item Description',
  SOR_ITEM_UNIT bigint(12) NOT NULL COMMENT 'Item Unit',
  SOR_BASIC_RATE decimal(15,2) DEFAULT NULL COMMENT 'Basic Rate',
  SOR_LABOUR_RATE decimal(15,2) DEFAULT NULL COMMENT 'Labour Rate',
  SORD_ACTIVE char(1) NOT NULL COMMENT 'Active/Inactive',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  LE_TO decimal(12,2) DEFAULT NULL COMMENT 'Lead Upto',
  LI_TO decimal(12,2) DEFAULT NULL COMMENT 'Lift Upto',
  LE_UNIT bigint(12) DEFAULT NULL COMMENT 'Lead Unit',
  PRIMARY KEY (SORD_ID),
  KEY FK_SOR_ID_idx (SOR_ID),
  CONSTRAINT FK_SOR_ID FOREIGN KEY (SOR_ID) REFERENCES tb_wms_sor_mast (SOR_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
