--liquibase formatted sql
--changeset nilima:V20181120125628__CR_TB_WMS_MBCHEKLIST_DETAIL_16112018.sql
CREATE TABLE TB_WMS_MBCHEKLIST_DETAIL (
  MBCD_ID bigint(12) NOT NULL COMMENT 'Primary key',
  MBC_ID bigint(12) NOT NULL COMMENT 'Primary key',
  SL_LABEL_ID bigint(12) NOT NULL COMMENT 'scrutiny label Id',
  LEVELS bigint(12) NOT NULL COMMENT 'scrutiny levels',
  MBCD_VALUE varchar(400) NOT NULL COMMENT 'scrutiny value\n',
  ORGID int(11) NOT NULL COMMENT 'organization id\n',
  CREATED_BY bigint(11) NOT NULL COMMENT 'user id\n',
  CREATED_DATE datetime NOT NULL COMMENT 'last modification date\n',
  UPDATED_BY bigint(11) DEFAULT NULL COMMENT 'last updated by\n',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'last updated date\n',
  LG_IP_MAC varchar(100) NOT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (MBCD_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;