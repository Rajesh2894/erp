--liquibase formatted sql
--changeset nilima:V20180427113431__CR_TB_SW_POPULATION_MAST.sql
CREATE TABLE TB_SW_POPULATION_MAST (
  POP_ID BIGINT(12) NOT NULL COMMENT 'Primary key',
  POP_YEAR BIGINT(12) NOT NULL COMMENT 'Population Year Pefix',
  COD_DWZID1 BIGINT(12) NOT NULL COMMENT 'Ward',
  COD_DWZID2 BIGINT(12) NULL COMMENT 'Zone',
  COD_DWZID3 BIGINT(12) NULL COMMENT 'Block',
  COD_DWZID4 BIGINT(12) NULL COMMENT 'Route',
  COD_DWZID5 BIGINT(12) NULL,
  POP_EST DECIMAL(20,2) NOT NULL COMMENT 'Popultion',
  ORGID VARCHAR(45) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (pop_id))
COMMENT = 'Population Mast';
