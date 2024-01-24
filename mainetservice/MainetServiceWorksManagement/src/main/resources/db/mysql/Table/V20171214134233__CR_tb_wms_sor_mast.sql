--liquibase formatted sql
--changeset nilima:V20171214134233__CR_tb_wms_sor_mast.sql
CREATE TABLE tb_wms_sor_mast (
  SOR_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  SOR_NAME varchar(250) NOT NULL COMMENT 'Schedule Rate Name',
  SOR_FROMDATE date NOT NULL COMMENT 'Schedule Rate Start Period',
  SOR_TODATE date NOT NULL COMMENT 'Schedule Rate End Period',
  SOR_WCATEGORY1 bigint(12) NOT NULL COMMENT 'Work Category1',
  SOR_WCATEGORY2 bigint(12) DEFAULT NULL COMMENT 'Work Category2',
  SOR_WCATEGORY3 bigint(12) DEFAULT NULL COMMENT 'Work Category3',
  SOR_WCATEGORY4 bigint(12) DEFAULT NULL COMMENT 'Work Category4',
  SOR_WCATEGORY5 bigint(12) DEFAULT NULL COMMENT 'Work Category5',
  SOR_ACTIVE char(1) NOT NULL,
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE date NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE date DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  SOR_TYPEID bigint(12) DEFAULT NULL,
  PRIMARY KEY (SOR_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;