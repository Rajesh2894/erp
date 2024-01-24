--liquibase formatted sql
--changeset nilima:V20180529180703__CR_TB_WMS_MEASUREMENTBOOK_MAST_HIST.sql
drop table if exists TB_WMS_MEASUREMENTBOOK_MAST_HIST;
--changeset nilima:V20180529180703__CR_TB_WMS_MEASUREMENTBOOK_MAST_HIST1.sql
CREATE TABLE TB_WMS_MEASUREMENTBOOK_MAST_HIST (
  MB_ID_H bigint(12)  COMMENT 'Primary Key',
  MB_ID bigint(12)  COMMENT 'Primary Key',
  CONT_ID bigint(12)  COMMENT 'Contract Id',
  MB_BRODATE datetime  COMMENT 'Measurement Brought Date',
  MB_TAKENDATE datetime  COMMENT 'Actual Measurement Taken Date',
  LEDGERNO varchar(100)  COMMENT 'Ledger no',
  PAGENO varchar(100)  COMMENT 'Page no',
  DESCRIPTION varchar(200)  COMMENT 'Description',
  MB_NO varchar(100)  COMMENT 'Measurement Book No',
  MB_STATUS char(3)  COMMENT '(D->Draft,P->Pending,A->Approved)',
  H_STATUS char(1)  COMMENT 'Status I->Insert,update',
  ORGID bigint(12)  COMMENT 'organization id',
  CREATED_BY bigint(12)  COMMENT 'user id who created the record',
  CREATED_DATE datetime  COMMENT 'record creation date',
  UPDATED_BY bigint(12)  COMMENT 'user id who updated the record',
  UPDATED_DATE datetime ,
  LG_IP_MAC varchar(100)  COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100)  COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MB_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MeasurementBook Master';