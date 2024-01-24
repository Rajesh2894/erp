--liquibase formatted sql
--changeset nilima:V20190328171742__AL_tb_wms_measurementbook_mast_hist_27032019.sql
drop table if exists tb_wms_measurementbook_mast_hist;

--liquibase formatted sql
--changeset nilima:V20190328171742__AL_tb_wms_measurementbook_mast_hist_270320191.sql
CREATE TABLE tb_wms_measurementbook_mast_hist(
  MB_ID_H bigint(12) NOT NULL COMMENT 'Primary Key',
  MB_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  WORKOR_ID bigint(12) DEFAULT NULL COMMENT 'Contract Id',
  MB_BRODATE datetime DEFAULT NULL COMMENT 'Measurement Brought Date',
  MB_TAKENDATE datetime DEFAULT NULL COMMENT 'Actual Measurement Taken Date',
  LEDGERNO varchar(100) DEFAULT NULL COMMENT 'Ledger no',
  PAGENO varchar(100) DEFAULT NULL COMMENT 'Page no',
  DESCRIPTION varchar(200) DEFAULT NULL COMMENT 'Description',
  MB_NO varchar(100) DEFAULT NULL COMMENT 'Measurement Book No',
  MB_STATUS char(3) DEFAULT NULL COMMENT '(D->Draft,P->Pending,A->Approved)',
  H_STATUS char(1) DEFAULT NULL COMMENT 'Status I->Insert,update',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (`MB_ID_H`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MeasurementBook Master';
