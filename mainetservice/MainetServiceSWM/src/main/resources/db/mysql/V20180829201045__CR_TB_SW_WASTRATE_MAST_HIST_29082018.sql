--liquibase formatted sql
--changeset nilima:V20180829201045__CR_TB_SW_WASTRATE_MAST_HIST_29082018.sql
CREATE TABLE TB_SW_WASTRATE_MAST_HIST (
  WRAT_ID_H bigint(12) NOT NULL,
  WRAT_ID bigint(12) DEFAULT NULL,
  COD_WAST bigint(12) DEFAULT NULL COMMENT 'First level Waste Type',
  COD_WAST1 bigint(12) DEFAULT NULL COMMENT 'last level Wast value',
  WRAT_RATE decimal(7,2) DEFAULT NULL,
  WRAT_FROM_DATE date DEFAULT NULL COMMENT 'Rate From Date',
  WRAT_TO_DATE date DEFAULT NULL COMMENT 'Rate to date',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Update->U,Delete->D',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL,
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WRAT_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;