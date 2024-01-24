--liquibase formatted sql
--changeset nilima:V20180829201136__CR_TB_WMS_RABILL_29082018.sql
CREATE TABLE TB_WMS_RABILL (
  RA_ID bigint(12) NOT NULL COMMENT 'Primary Key',
  PROJ_ID bigint(12) NOT NULL COMMENT 'Project Name',
  WORK_ID bigint(12) NOT NULL COMMENT 'Work Name',
  RA_GENEDATE date NOT NULL COMMENT 'RA Generation Date',
  RA_BILLNO varchar(40) DEFAULT NULL COMMENT 'RA Bill No',
  RA_BILLDT date DEFAULT NULL COMMENT 'RA Bill Date',
  RA_BILLAMT decimal(15,2) NOT NULL COMMENT 'RA Generation Date',
  RA_MBID varchar(200) DEFAULT NULL COMMENT 'MB Selected for RA',
  RA_MBAMT decimal(15,2) DEFAULT NULL,
  RA_TAXAMT decimal(15,2) DEFAULT NULL COMMENT 'RA TaxAmount',
  RA_PAIDAMT decimal(15,2) DEFAULT NULL COMMENT 'RA Paid Amount',
  RA_REMARK varchar(200) DEFAULT NULL COMMENT 'RA Paid Amount',
  RA_STATUS char(1) NOT NULL COMMENT 'RA Status',
  ORGID bigint(12) NOT NULL COMMENT 'organization id',
  CREATED_BY bigint(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE datetime NOT NULL COMMENT 'record creation date',
  UPDATED_BY bigint(12) DEFAULT NULL COMMENT 'user id who updated the record',
  UPDATED_DATE datetime DEFAULT NULL COMMENT 'date on which updated the record',
  LG_IP_MAC varchar(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD varchar(100) DEFAULT NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (RA_ID),
  KEY FK_RA_PROJID_idx (PROJ_ID),
  KEY FK_RA_WORK_ID_idx (WORK_ID),
  CONSTRAINT FK_RA_PROJID FOREIGN KEY (PROJ_ID) REFERENCES tb_wms_project_mast (PROJ_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_RA_WORK_ID FOREIGN KEY (WORK_ID) REFERENCES tb_wms_workdefination (WORK_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;