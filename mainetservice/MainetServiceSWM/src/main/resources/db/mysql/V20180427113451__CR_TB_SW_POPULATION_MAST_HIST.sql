--liquibase formatted sql
--changeset nilima:V20180427113451__CR_TB_SW_POPULATION_MAST_HIST.sql
CREATE TABLE TB_SW_POPULATION_MAST_HIST (
  POP_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  POP_ID bigint(12)  COMMENT 'Primary key',
  POP_YEAR bigint(12)  COMMENT 'Population Year Pefix',
  COD_DWZID1 bigint(12)  COMMENT 'Ward',
  COD_DWZID2 bigint(12) DEFAULT NULL COMMENT 'Zone',
  COD_DWZID3 bigint(12) DEFAULT NULL COMMENT 'Block',
  COD_DWZID4 bigint(12) DEFAULT NULL COMMENT 'Route',
  COD_DWZID5 bigint(12) DEFAULT NULL,
  POP_EST decimal(20,2)  COMMENT 'Popultion',
  H_STATUS CHAR(1) COMMENT 'Status I->Insert,update',
  ORGID varchar(45)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (POP_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Population Mast History';
