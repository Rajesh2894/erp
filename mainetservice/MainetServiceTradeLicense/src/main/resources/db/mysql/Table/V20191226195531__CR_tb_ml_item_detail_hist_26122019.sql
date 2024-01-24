--liquibase formatted sql
--changeset Anil:V20191226195531__CR_tb_ml_item_detail_hist_26122019.sql
drop table if exists tb_ml_item_detail_hist;
--liquibase formatted sql
--changeset Anil:V20191226195531__CR_tb_ml_item_detail_hist_261220191.sql
CREATE TABLE tb_ml_item_detail_hist (
  TRI_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  TRI_ID bigint(12) NOT NULL COMMENT 'TRI ID',
  TRD_ID bigint(12) NOT NULL COMMENT 'Foregin Key',
  TRI_COD1 bigint(12) NOT NULL COMMENT 'Item Code1',
  TRI_COD2 bigint(12) DEFAULT NULL COMMENT 'Item Code2',
  TRI_COD3 bigint(12) DEFAULT NULL COMMENT 'Item Code3',
  TRI_COD4 bigint(12) DEFAULT NULL COMMENT 'Item Code4',
  TRI_COD5 bigint(12) DEFAULT NULL COMMENT 'Item Code5',
  TRI_RATE decimal(15,2) DEFAULT NULL COMMENT 'Rate',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  TRI_STATUS char(2) DEFAULT NULL,
  TRD_QUANTITY decimal(15,2) DEFAULT NULL,
  TRD_UNIT bigint(12) DEFAULT NULL,
  PRIMARY KEY (TRI_ID_H),
  KEY FK_TRD_ID_idx1 (TRD_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
