--liquibase formatted sql
--changeset nilima:V20180609165527__CR_TB_SW_TRIPSHEET_HIST_08062018.sql
CREATE TABLE TB_SW_TRIPSHEET_HIST (
  TRIP_ID_H bigint(12) NOT NULL,
  TRIP_ID bigint(12) DEFAULT NULL COMMENT 'Primary Key',
  VE_ID bigint(12) DEFAULT NULL COMMENT 'Vehicle Id',
  TRIP_DRIVERNAME varchar(100) DEFAULT NULL COMMENT 'Driver  Name',
  TRIP_INTIME datetime DEFAULT NULL COMMENT 'In Time',
  TRIP_OUTTIME datetime DEFAULT NULL COMMENT 'Out Time',
  TRIP_ENTWEIGHT bigint(12) DEFAULT NULL COMMENT 'Entry Weight',
  TRIP_EXITWEIGHT bigint(12) DEFAULT NULL,
  TRIP_TOTALGARBAGE bigint(20) DEFAULT NULL,
  TRIP_WESLIPNO varchar(50) DEFAULT NULL,
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  PRIMARY KEY (TRIP_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tripsheet Histroy';