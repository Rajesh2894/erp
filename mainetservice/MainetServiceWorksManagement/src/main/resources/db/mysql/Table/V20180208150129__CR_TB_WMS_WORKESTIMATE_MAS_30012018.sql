--liquibase formatted sql
--changeset priya:V20180208150129__CR_TB_WMS_WORKESTIMATE_MAS_30012018.sql
CREATE TABLE TB_WMS_WORKESTIMATE_MAS (
  WORKE_ID BIGINT(12) NOT NULL COMMENT 'Primary Jey',
  WORK_ID BIGINT(12) NOT NULL COMMENT 'foregin key TB_WMS_WORKDEFINATION',
  WORKE_ESTIMATE_TYPE CHAR(1) NOT NULL COMMENT '(Estimate from SOR->\'S\',From Previous Estimate->\'P\',Upload Estimate->\'U\')',
  WORKE_PID BIGINT(12) NULL COMMENT 'Parent id of primary key',
  WORKD_File_Name VARCHAR(500) NULL COMMENT 'when measuremnet upoaded',
  WORKE_PRE_ESTID BIGINT(12) NULL COMMENT 'From Previous Estimate',
  SOR_ID BIGINT(12) NULL COMMENT 'foregin key TB_WMS_SOR_MAS(Estimate from SOR->\'S\')',
  SORD_ID BIGINT(12) NULL COMMENT 'foregin key TB_WMS_SOR_DET(Estimate from SOR->\'S\')',
  MA_ID BIGINT(12) NULL COMMENT 'foregin key TB_WMS_GEN_RATE_MAST(Estimate from SOR->\'S\')',
  MA_PID BIGINT(12) NULL COMMENT 'Parent id of primary key of TB_WMS_GEN_RATE_MAST',
  SORD_CATEGORY BIGINT(12) NULL COMMENT 'Category',
  SORD_SUBCATEGORY VARCHAR(2000) NULL COMMENT 'Sub Category',
  SORD_ITEMNO VARCHAR(50) NULL COMMENT 'Item Number',
  SORD_DESCRIPTION VARCHAR(2000) NULL COMMENT 'Item Description',
  SORD_ITEM_UNIT BIGINT(12) NOT NULL COMMENT 'Item Unit from prefix',
  SORD_BASIC_RATE DECIMAL(15,2) NOT NULL COMMENT 'Basic Rate',
  SORD_LABOUR_RATE DECIMAL(15,2) NULL COMMENT 'Labour Rate\n',
  WORKE_QUANTITY DECIMAL(5) NOT NULL COMMENT 'Quantity',
  WORKE_AMOUNT DECIMAL(20,2) NOT NULL COMMENT 'Total (Quantity*Rate)',
  WORKE_FLAG CHAR(3) NOT NULL COMMENT '(S->\'SOR ITEM\',\'M\'->Material,\'RO\'->Royalty,\'LO\'->LOAD,\'UN\'->\'Unload\',\'LE\'->\'Lead\',\'LF\'->LIFT,\'ST\'->\'Stacking\',\'L\'->Labour,\'C\'->Machinary,\'N\'->\'NON-SOR\')\n',
  WORKE_ACTIVE CHAR(1) NOT NULL COMMENT 'Status (\'Y\'-> Active,\'N\'->Inactive)',
  ORGID BIGINT(12) NOT NULL COMMENT 'organization id',
  CREATED_BY BIGINT(12) NOT NULL COMMENT 'user id who created the record',
  CREATED_DATE DATETIME NOT NULL COMMENT 'record creation date',
  UPDATED_BY BIGINT(12) NULL COMMENT 'user id who updated the record',
  UPDATED_DATE DATETIME NULL COMMENT 'date on which updated the record',
  LG_IP_MAC VARCHAR(100) NOT NULL COMMENT 'machine ip address from where user has created the record',
  LG_IP_MAC_UPD VARCHAR(100) NULL COMMENT 'machine ip address from where user has updated the record',
  PRIMARY KEY (WORKE_ID),
  INDEX FK_WE_WORK_ID_idx (WORK_ID ASC),
  INDEX FK_WE_SOR_ID_idx (SOR_ID ASC),
  INDEX FK_WE_SORD_ID_idx (SORD_ID ASC),
  INDEX FK_WE_MA_ID_idx (MA_ID ASC),
  CONSTRAINT FK_WE_WORK_ID
    FOREIGN KEY (WORK_ID)
    REFERENCES tb_wms_workdefination (WORK_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_WE_SOR_ID
    FOREIGN KEY (SOR_ID)
    REFERENCES tb_wms_sor_mast (SOR_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_WE_SORD_ID
    FOREIGN KEY (SORD_ID)
    REFERENCES tb_wms_sor_det (SORD_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_WE_MA_ID
    FOREIGN KEY (MA_ID)
    REFERENCES tb_wms_gen_rate_mast (MA_ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = 'Work Estimate';
