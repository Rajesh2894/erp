--liquibase formatted sql
--changeset nilima:V20180609170032__CR_TB_SW_EMPLOYEE_SCHEDULING_07062018.sql
CREATE TABLE TB_SW_EMPLOYEE_SCHEDULING (
  EMS_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  EMS_TYPE BIGINT(12) NULL COMMENT 'Schedule Type',
  EMS_FROMDATE DATETIME NULL COMMENT 'Schedule From date',
  EMS_TODATE DATETIME NULL COMMENT 'Schedule To date',
  EMS_REOCC CHAR(1) NULL COMMENT 'Reoccurance(D->Daily,W->Weekly,M->)',
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (EMS_ID))
COMMENT = 'Employee Scheduling';