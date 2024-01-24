--liquibase formatted sql
--changeset Anil:V20191226195549__CR_tb_ml_trade_mast_hist_126122019.sql
drop table if exists tb_ml_trade_mast_hist;
--liquibase formatted sql
--changeset Anil:V20191226195549__CR_tb_ml_trade_mast_hist_1261220191.sql
CREATE TABLE tb_ml_trade_mast_hist (
  TRD_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  TRD_ID bigint(12) NOT NULL COMMENT 'TRD ID',
  APM_APPLICATION_ID bigint(16) DEFAULT NULL,
  TRD_TYPE bigint(12) DEFAULT NULL COMMENT 'Manufacturer/Retailer/wholeseller/Other',
  TRD_LICTYPE bigint(12) NOT NULL COMMENT 'Temparary/Permanat',
  TRD_BUSNM varchar(50) NOT NULL COMMENT 'Businees Name',
  TRD_BUSADD varchar(200) NOT NULL COMMENT 'Businees Address',
  TRD_LICNO varchar(50) DEFAULT NULL COMMENT 'System Generated Lic number',
  TRD_OLDLICNO varchar(100) DEFAULT NULL COMMENT 'Businees Address',
  TRD_LICFROM_DATE date DEFAULT NULL COMMENT 'License From',
  TRD_LICTO_DATE date DEFAULT NULL COMMENT 'License To',
  TRD_LICISDATE date DEFAULT NULL COMMENT 'License Issue Date',
  TRD_WARD1 bigint(12) NOT NULL COMMENT 'Ward1',
  TRD_WARD2 bigint(12) DEFAULT NULL COMMENT 'Ward2',
  TRD_WARD3 bigint(12) DEFAULT NULL COMMENT 'Ward3',
  TRD_WARD4 bigint(12) DEFAULT NULL COMMENT 'Ward4',
  TRD_WARD5 bigint(12) DEFAULT NULL COMMENT 'Ward5',
  TRD_FTYPE bigint(12) DEFAULT NULL,
  TRD_FAREA varchar(20) DEFAULT NULL,
  PM_PROPNO varchar(20) DEFAULT NULL,
  TRD_OWNERNM varchar(100) DEFAULT NULL,
  TRD_ETY varchar(2) DEFAULT NULL COMMENT 'Entry Type(''S''-> Service,''D''->Data Entry)',
  TRD_STATUS char(1) NOT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  TRD_FLAT_NO varchar(250) DEFAULT NULL,
  PRIMARY KEY (TRD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
