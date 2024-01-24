--liquibase formatted sql
--changeset priya:V20180208150023__CR_tb_wms_workdefination_year_det_30012018.sql
CREATE TABLE tb_wms_workdefination_year_det (
  YE_ID BIGINT(12) NOT NULL COMMENT 'Primary Key',
  WORK_ID BIGINT(12) NOT NULL COMMENT 'foregin key TB_WMS_WORKESTIMATE_MAST',
  SAC_HEAD_ID BIGINT(12) NULL COMMENT 'BugetCode',
  FA_YEARID BIGINT(12) NULL COMMENT 'Financiale Year Id',
  YE_PERCENT_WORK DECIMAL(6,2) NULL COMMENT 'Percentage',
  YE_DOC_REFERENCENO VARCHAR(50) NULL COMMENT 'Document Reference Number',
  YE_BUGEDED_AMOUNT DECIMAL(15,2) NULL COMMENT 'Bugeted Amount',
  YE_ACTIVE CHAR(1) NULL COMMENT 'Active/Inactive',
  ORGID BIGINT(12) NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (YE_ID),
  INDEX FK_WORK_ID_idx (WORK_ID ASC),
  INDEX FK_WORK_SACHEADID_idx (SAC_HEAD_ID ASC),
  INDEX FK_WORK_FAYEARID_idx (FA_YEARID ASC),
  CONSTRAINT FK_WORK_ID
    FOREIGN KEY (WORK_ID)
    REFERENCES tb_wms_workdefination (WORK_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_WORK_SACHEADID
    FOREIGN KEY (SAC_HEAD_ID)
    REFERENCES tb_ac_secondaryhead_master (SAC_HEAD_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_WORK_FAYEARID
    FOREIGN KEY (FA_YEARID)
    REFERENCES tb_financialyear (FA_YEARID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Work defination year detail';
