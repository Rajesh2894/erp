--liquibase formatted sql
--changeset nilima:V20180829201000__CR_TB_SW_WASTRATE_MAST_29082018.sql
CREATE TABLE TB_SW_WASTRATE_MAST (
  WRAT_ID bigint(12) NOT NULL,
  COD_WAST bigint(12) NOT NULL COMMENT 'First level Waste Type',
  COD_WAST1 bigint(12) NOT NULL COMMENT 'last level Wast value',
  WRAT_RATE decimal(7,2) NOT NULL,
  WRAT_FROM_DATE date NOT NULL COMMENT 'Rate From Date',
  WRAT_TO_DATE date NOT NULL COMMENT 'Rate to date',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WRAT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;