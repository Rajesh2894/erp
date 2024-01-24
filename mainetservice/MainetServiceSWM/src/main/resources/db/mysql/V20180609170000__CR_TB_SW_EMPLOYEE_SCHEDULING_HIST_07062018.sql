--liquibase formatted sql
--changeset nilima:V20180609170000__CR_TB_SW_EMPLOYEE_SCHEDULING_HIST_07062018.sql
CREATE TABLE TB_SW_EMPLOYEE_SCHEDULING_HIST (
  EMS_ID_H bigint(12) NOT NULL,
  EMS_ID bigint(12)  COMMENT 'Primary Key',
  EMS_TYPE bigint(12)  COMMENT 'Schedule Type',
  EMS_FROMDATE datetime  COMMENT 'Schedule From date',
  EMS_TODATE datetime  COMMENT 'Schedule To date',
  EMS_REOCC char(1)  COMMENT 'Reoccurance(D->Daily,W->Weekly,M->)',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime  COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (EMS_ID_H)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Employee Scheduling history';
