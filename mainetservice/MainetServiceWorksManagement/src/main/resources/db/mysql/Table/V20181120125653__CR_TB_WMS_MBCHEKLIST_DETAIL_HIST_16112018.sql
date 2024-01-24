--liquibase formatted sql
--changeset nilima:V20181120125653__CR_TB_WMS_MBCHEKLIST_DETAIL_HIST_16112018.sql
CREATE TABLE TB_WMS_MBCHEKLIST_DETAIL_HIST (
  MBCD_ID_H bigint(12) NOT NULL COMMENT 'Primary key',
  MBCD_ID bigint(12)  COMMENT 'Primary key',
  MBC_ID bigint(12)  COMMENT 'Primary key',
  SL_LABEL_ID bigint(12)  COMMENT 'scrutiny label Id',
  LEVELS bigint(12)  COMMENT 'scrutiny levels',
  MBCD_VALUE varchar(400)  COMMENT 'scrutiny value\n',
  H_STATUS char(1)  COMMENT 'History Status',
  ORGID int(11)  COMMENT 'organization id\n',
  CREATED_BY bigint(11)  COMMENT 'user id\n',
  CREATED_DATE datetime  COMMENT 'last modification date\n',
  UPDATED_BY bigint(11)  COMMENT 'last updated by\n',
  UPDATED_DATE datetime  COMMENT 'last updated date\n',
  LG_IP_MAC varchar(100) ,
  LG_IP_MAC_UPD varchar(100) ,
  PRIMARY KEY (MBCD_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;