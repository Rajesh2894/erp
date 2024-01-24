--liquibase formatted sql
--changeset nilima:V20180829201207__CR_TB_WMS_RABILL_TAXD_29082018.sql
CREATE TABLE TB_WMS_RABILL_TAXD (
  RA_TAXID bigint(12) NOT NULL COMMENT 'Primary key',
  RA_ID bigint(12) DEFAULT NULL COMMENT 'FK tb_wms_measurementbook_mast',
  TAX_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_TAX_MAST',
  RA_TAXTYPE char(1) DEFAULT NULL COMMENT 'A->Addition D->Deduction',
  RA_TAXFACT bigint(12) DEFAULT NULL COMMENT 'MB Tax Factor (Amount,Percentage)',
  RA_TAXPER decimal(15,2) DEFAULT NULL,
  RA_TAXVALUE decimal(15,2) DEFAULT NULL COMMENT 'MB Tax Value',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (RA_TAXID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='RABill Tax Detail';
