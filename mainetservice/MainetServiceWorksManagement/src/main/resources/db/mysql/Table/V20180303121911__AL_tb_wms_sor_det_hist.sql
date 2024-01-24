--liquibase formatted sql
--changeset priya:V20180303121911__AL_tb_wms_sor_det_hist.sql
DROP TABLE IF EXISTS tb_wms_sor_det_hist;
--liquibase formatted sql
--changeset priya:V20180303121911__AL_tb_wms_sor_det_hist1.sql
CREATE TABLE tb_wms_sor_det_hist(
  SORD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  SORD_ID bigint(12) NOT NULL,
  SOR_ID bigint(12) NOT NULL COMMENT 'Schedule Rate Name',
  SORD_CATEGORY bigint(12) DEFAULT NULL COMMENT 'Category',
  SORD_SUBCATEGORY varchar(2000) DEFAULT NULL,
  SORD_ITEMNO varchar(50) NOT NULL COMMENT 'Item Number',
  SORD_DESCRIPTION varchar(2000) NOT NULL COMMENT 'Item Description',
  SOR_ITEM_UNIT bigint(12) NOT NULL COMMENT 'Item Unit',
  SOR_BASIC_RATE decimal(15,2) DEFAULT NULL COMMENT 'Basic Rate',
  SOR_LABOUR_RATE decimal(15,2) DEFAULT NULL COMMENT 'Labour Rate',
  LE_TO decimal(12,2) DEFAULT NULL COMMENT 'Lead Upto',
  LI_TO decimal(12,2) DEFAULT NULL COMMENT 'Lift Upto',
  LE_UNIT bigint(12) DEFAULT NULL COMMENT 'Lead Unit',
  SORD_ACTIVE char(1) NOT NULL COMMENT 'Active/Inactive',
  H_STATUS CHAR(1) NULL COMMENT 'History Status',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SORD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;