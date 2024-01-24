--liquibase formatted sql
--changeset priya:V20180303122014__AL_tb_wms_sor_mast_hist.sql
DROP TABLE IF EXISTS tb_wms_sor_mast_hist;
--liquibase formatted sql
--changeset priya:V20180303122014__AL_tb_wms_sor_mast_hist1.sql
CREATE TABLE tb_wms_sor_mast_hist (
  SOR_ID_h bigint(12) NOT NULL COMMENT 'Primary Key',
  SOR_ID bigint(12) NOT NULL COMMENT 'Foregin Primary Key',
  SOR_CPD_ID varchar(45) DEFAULT NULL COMMENT 'SOR name from prefix',
  SOR_NAME varchar(250) NOT NULL COMMENT 'Schedule Rate Name',
  SOR_FROMDATE date NOT NULL COMMENT 'Schedule Rate Start Period',
  SOR_TODATE date DEFAULT NULL COMMENT 'Schedule Rate End Period',
  SOR_ACTIVE char(1) NOT NULL COMMENT 'Schedule Rate Active/Inactive',
  H_STATUS   char(1) NOT NULL COMMENT 'History Status',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (SOR_ID_h)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;