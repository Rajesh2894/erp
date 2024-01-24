--liquibase formatted sql
--changeset nilima:V20180529175711__CR_TB_WMS_MEASUREMENTBOOK_MAST.sql
drop table if exists TB_WMS_MEASUREMENTBOOK_MAST;
--changeset nilima:V20180529175711__CR_TB_WMS_MEASUREMENTBOOK_MAST1.sql
CREATE TABLE TB_WMS_MEASUREMENTBOOK_MAST (
  MB_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  WORKOR_ID bigint(12) NOT NULL COMMENT 'Contract Id',
  MB_BRODATE datetime NOT NULL COMMENT 'Measurement Brought Date',
  MB_TAKENDATE datetime NOT NULL COMMENT 'Actual Measurement Taken Date',
  LEDGERNO varchar(100) DEFAULT NULL COMMENT 'Ledger no',
  PAGENO varchar(100) DEFAULT NULL COMMENT 'Page no',
  DESCRIPTION varchar(200) DEFAULT NULL COMMENT 'Description',
  MB_NO varchar(100) DEFAULT NULL COMMENT 'Measurement Book No',
  MB_STATUS char(3) NOT NULL COMMENT '(D->Draft,P->Pending,A->Approved)',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (MB_ID),
  KEY FK_WORKOR_ID_idx (WORKOR_ID),
  CONSTRAINT FK_WORKOR_ID FOREIGN KEY (WORKOR_ID) REFERENCES tb_wms_workeorder (WORKOR_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MeasurementBook Master';