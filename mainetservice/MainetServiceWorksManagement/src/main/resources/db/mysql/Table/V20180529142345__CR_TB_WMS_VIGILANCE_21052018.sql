--liquibase formatted sql
--changeset nilima:V20180529142345__CR_TB_WMS_VIGILANCE_21052018.sql
CREATE TABLE TB_WMS_VIGILANCE (
  VI_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  VI_REFTYPE CHAR(1) NOT NULL COMMENT '(M->MB,B->Bill)',
  VI_MEMODATE DATE NULL COMMENT 'Memo Date',
  VI_INSPECTIONDATE DATE NULL COMMENT 'Inspection Date',
  VI_MEMOTYPE CHAR(1) NULL COMMENT 'A->Actionable,I->Informational',
  VI_MEMODESC VARCHAR(500) NULL COMMENT 'Memo Description',
  VI_STATUS CHAR(1) NULL COMMENT 'O->Open,C->Closed,I->InProgress',
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL,
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (VI_ID))
COMMENT = 'Vigilance';