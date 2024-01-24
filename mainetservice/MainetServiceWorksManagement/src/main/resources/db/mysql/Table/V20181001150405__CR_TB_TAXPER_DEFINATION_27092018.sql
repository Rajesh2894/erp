--liquibase formatted sql
--changeset nilima:V20181001150405__CR_TB_TAXPER_DEFINATION_27092018.sql
CREATE TABLE TB_TAXPER_DEFINATION (
  TAX_DEFID bigint(12) NOT NULL COMMENT 'Primary key',
  TAX_ID bigint(12) DEFAULT NULL COMMENT 'FK TB_TAX_MAST',
  RA_TAXTYPE char(1) DEFAULT NULL COMMENT 'A->Addition D->Deduction',
  RA_TAXFACT bigint(12) DEFAULT NULL COMMENT 'Tax Factor (Amount,Percentage)',
  RA_TAXPER decimal(15,2) DEFAULT NULL,
  RA_TAXVALUE decimal(15,2) DEFAULT NULL COMMENT 'MB Tax Value',
  ORGID bigint(12) DEFAULT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime DEFAULT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL,
  UPDATED_DATE datetime DEFAULT NULL,
  LG_IP_MAC varchar(100) DEFAULT NULL,
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL,
  PRIMARY KEY (TAX_DEFID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tax Percentage Defination';